package CONTROLEUR;

import DAO.*;
import MODELE.*;
import VUE.Vue;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Controleur {
    private Vue vue;
    private EntityManager em;

    public Controleur(EntityManager em, Vue v) {
        this.vue = v;
        this.em = em;
    }

    /**
     * Execute le programme. Par le biais de la fonction Vue#scanCommand(), elle attend une entree de l'utilisateur. Selon l'entree
     * l'affichage est le suivant :
     * "1" pour afficher la liste des consommations où le tarif creux d'identifiant "id" est specifie
     * "2" pour calculer et afficher la depense totale d'un compteur a un jour donne
     * @return vrai si l'execution s'est bien passee, faux si la commande entree par l'utilisateur est non valide.
     */
    public boolean execute() {
        String cmd = this.vue.scanCommand();
        if (cmd.equals("1")) {
            //Question 1 : liste des consommation pour un tarif creux
            this.vue.display("Entrez un id de tarif creux : ");
            int id = Integer.parseInt(this.vue.scanCommand());
            List<Consommation> consommations = ConsommationDAO.getConsommationsByTarifCreux(this.em, TarifCreuxDAO.find(this.em, id));
            for(Consommation c : consommations) {
                this.vue.display(c.toString());
            }
        } else if (cmd.equals("2")) {
            // Question 2
            this.vue.display("Entrez un compteur : ");
            int cmptr = this.vue.scanInteger();
            this.vue.display("Entrez une date au format AAAA-MM-JJ: ");
            LocalDate date = this.vue.scanDate();
            this.vue.displayCost(CompteurDAO.computeCost(em, CompteurDAO.find(em, cmptr), date));
        } else {
            this.vue.display("ERREUR : Commande non reconnue. Fin.");
            return false;
        }
        return true;
    }

    /**
     * Rempli la base de donnees MySQL par des enregistrements arbitraires 3 personnes, 3 compteurs, 3 adresses,
     * 3 consommations and 3 tarifs tels que :
     * - les tarif1, 2, et 3 : plein tarif entre 15:00 et 19:00 + prix resp. 1€, 2€, 3€ + reduction 10%, 20%, 30%
     * - conso1 le 2019-01-09 de 10:00 a 18:00 pour une puissance de 100kWh avec tarif1 et tarif2
     * - conso2 le 2019-01-09 de 22:00 a 23:59 pour une puissance de 100kWh avec tarif2
     * - conso3 le 2019-01-09 de 10:00 a 23:59 pour une puissance de 100kWh avec tarif3
     * La fonction utilise les fonctions definies dans les DAO.
     */
    public void populateDB() {
        LocalDate date1 = LocalDate.of(2019, 1, 9);
        LocalDate date2 = LocalDate.of(2019, 1, 10);
        LocalTime time1 = LocalTime.of(10, 0);
        LocalTime time2 = LocalTime.of(22, 0);
        LocalTime debutCreux = LocalTime.of(19, 0);
        LocalTime finCreux = LocalTime.of(15, 0);
        LocalTime time4 = LocalTime.of(23, 59);

        try {
            // Test of Personne, Adresse and Compteur + DAO
            Personne p1 = PersonneDAO.createPersonne(this.em, 11111, "0606060606");
            Personne p2 = PersonneDAO.createPersonne(this.em, 22222, "0707070707");
            Personne p3 = PersonneDAO.createPersonne(this.em, 33333, "0808080808");

            Adresse a1 = AdresseDAO.createAdresse(this.em, "Parmentier", "Tours");
            Adresse a2 = AdresseDAO.createAdresse(this.em, "Eupatoria", "Tours");
            Adresse a3 = AdresseDAO.createAdresse(this.em, "Bordeaux", "Tours");

            p1 = PersonneDAO.updateAdresse(this.em, p1, a1);
            p2 = PersonneDAO.updateAdresse(this.em, p2, a2);
            p3 = PersonneDAO.updateAdresse(this.em, p3, a3);

            Compteur compteur1 = CompteurDAO.createCompteur(this.em, date1, a1);
            Compteur compteur2 = CompteurDAO.createCompteur(this.em, date1, a2);
            Compteur compteur3 = CompteurDAO.createCompteur(this.em, date1, a3);

            CompteurDAO.updatePersonne(this.em, compteur1, p1);
            CompteurDAO.updatePersonne(this.em, compteur2, p2);
            CompteurDAO.updatePersonne(this.em, compteur3, p3);

            Consommation cons1 = ConsommationDAO.createConsommation(this.em, date1, time1, time2, 100, compteur1);
            Consommation cons2 = ConsommationDAO.createConsommation(this.em, date1, time2, time4, 100, compteur1);
            Consommation cons3 = ConsommationDAO.createConsommation(this.em, date1, time1, time4, 100, compteur1);

            compteur1 = CompteurDAO.addConsommation(this.em, compteur1, cons1);
            compteur1 = CompteurDAO.addConsommation(this.em, compteur1, cons2);
            compteur2 = CompteurDAO.addConsommation(this.em, compteur2, cons2);
            compteur3 = CompteurDAO.addConsommation(this.em, compteur3, cons3);

            Tarif tarif1 = TarifDAO.createTarif(this.em, 1, 0.1, debutCreux, finCreux);
            Tarif tarif2 = TarifDAO.createTarif(this.em, 2, 0.2, debutCreux, finCreux);
            Tarif tarif3 = TarifDAO.createTarif(this.em, 3, 0.3, debutCreux, finCreux);

            cons1 = ConsommationDAO.addTarif(this.em, cons1, tarif1);
            cons1 = ConsommationDAO.addTarif(this.em, cons1, tarif2);
            cons2 = ConsommationDAO.addTarif(this.em, cons2, tarif2);
            cons3 = ConsommationDAO.addTarif(this.em, cons3, tarif3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
