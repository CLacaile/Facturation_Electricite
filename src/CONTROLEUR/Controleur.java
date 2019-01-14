package CONTROLEUR;

import DAO.CompteurDAO;
import DAO.ConsommationDAO;
import DAO.TarifCreuxDAO;
import MODELE.Consommation;
import MODELE.Modele;
import VUE.Vue;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class Controleur {
    private Modele modele;
    private Vue vue;
    private EntityManager em;

    public Controleur(EntityManager em, Modele m, Vue v) {
        this.modele = m;
        this.vue = v;
        this.em = em;
    }

    public Vue getVue() {
        return vue;
    }

    public void setVue(Vue vue) {
        this.vue = vue;
    }

    public boolean execute() {
        String cmd = this.vue.scanCommand();
        if(cmd.equals("1")) {
            //Question 1 : liste des consommation pour un tarif creux
            this.vue.display("Entrez un id de tarif creux : ");
            int id = this.vue.scanInteger();
            this.vue.displayConsommations(ConsommationDAO.getConsommationsByTarifCreux(this.em, TarifCreuxDAO.find(this.em, id)));
        }
        else if(cmd.equals("2")) {
            // Question 2
            this.vue.display("Entrez un compteur : ");
            int cmptr = this.vue.scanInteger();
            this.vue.display("Entrez une date au format AAAA-MM-JJ: ");
            LocalDate date = this.vue.scanDate();
            this.vue.displayCost(CompteurDAO.computeCost(em, CompteurDAO.find(em, cmptr), date));
        }
        else {
            this.vue.display("ERREUR : Commande non reconnue. Fin.");
            return false;
        }
        return true;
    }
}
