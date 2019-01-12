import DAO.*;
import MODELE.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bdd_hibernate");
        EntityManager em = emf.createEntityManager();

        LocalDate date1 = LocalDate.of(2019, 1, 9);
        LocalDate date2 = LocalDate.of(2019, 1, 10);
        LocalTime time1 = LocalTime.of(10,0);
        LocalTime debutCreux = LocalTime.of(15,0);
        LocalTime finCreux = LocalTime.of(19,0);
        LocalTime time4 = LocalTime.of(23, 59);

        try {
            // Test of Personne, Adresse and Compteur + DAO
            Personne p1 = PersonneDAO.createPersonne(em, 11111, "0606060606");
            Personne p2 = PersonneDAO.createPersonne(em, 22222, "0707070707");
            Personne p3 = PersonneDAO.createPersonne(em, 33333, "0808080808");

            Adresse a1 = AdresseDAO.createAdresse(em, "Parmentier", "Tours");
            Adresse a2 = AdresseDAO.createAdresse(em, "Eupatoria", "Tours");
            Adresse a3 = AdresseDAO.createAdresse(em, "Bordeaux", "Tours");

            p1 = PersonneDAO.updateAdresse(em, p1, a1);
            p2 = PersonneDAO.updateAdresse(em, p2, a2);
            p3 = PersonneDAO.updateAdresse(em, p3, a3);

            Compteur compteur1 = CompteurDAO.createCompteur(em, date1, a1);
            Compteur compteur2 = CompteurDAO.createCompteur(em, date1, a2);
            Compteur compteur3 = CompteurDAO.createCompteur(em, date1, a3);

            CompteurDAO.updatePersonne(em, compteur1, p1);
            CompteurDAO.updatePersonne(em, compteur2, p2);
            CompteurDAO.updatePersonne(em, compteur3, p3);

            Consommation cons1 = ConsommationDAO.createConsommation(em, date1, time1, time4, 10, compteur1);
            Consommation cons2 = ConsommationDAO.createConsommation(em, date1, time1, time4, 20, compteur1);
            Consommation cons3 = ConsommationDAO.createConsommation(em, date1, time1, time4, 30, compteur1);

            compteur1 = CompteurDAO.addConsommation(em, compteur1, cons1);
            compteur2 = CompteurDAO.addConsommation(em, compteur2, cons2);
            compteur3 = CompteurDAO.addConsommation(em, compteur3, cons3);

            Tarif tarif1 = TarifDAO.createTarif(em, 1, 0.11, debutCreux, finCreux);
            Tarif tarif2 = TarifDAO.createTarif(em, 2, 0.22, debutCreux, finCreux);
            Tarif tarif3 = TarifDAO.createTarif(em, 3, 0.33, debutCreux, finCreux);

            cons1 = ConsommationDAO.addTarif(em, cons1, tarif1);
            cons2 = ConsommationDAO.addTarif(em, cons2, tarif2);
            cons3 = ConsommationDAO.addTarif(em, cons3, tarif3);

            System.out.println(PersonneDAO.find(em, 11111).getNumSS());

            // Question 2 : combien vaut la conso des compteurs pour la journée date1 ?

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}