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
        LocalTime time2 = LocalTime.of(15,0);
        LocalTime time3 = LocalTime.of(19,0);
        LocalTime time4 = LocalTime.of(23, 59);

        try {
            // Test of Personne, Adresse and Compteur + DAO
            Personne p = PersonneDAO.createPersonne(em, 11111, "0606060606");
            Adresse a = AdresseDAO.createAdresse(em, "Parmentier", "Tours");
            p = PersonneDAO.updateAdresse(em, p, a);
            Compteur c = CompteurDAO.createCompteur(em, date1, a);
            CompteurDAO.updatePersonne(em, c, p);
            System.out.println(PersonneDAO.find(em, 11111).getNumSS());

            // Test of Consommation, Horaires + DAO
            Consommation cons = ConsommationDAO.createConsommation(em, c);
            Horaires h1 = HorairesDAO.createHoraires(em, date1, time1, time2);
            Horaires h2 = HorairesDAO.createHoraires(em, date1, time2, time3);
            ConsommationDAO.addHoraires(em, cons, h1);
            ConsommationDAO.addHoraires(em, cons, h2);



            Tarif tarif1 = TarifDAO.createTarif(em, 1, 0.2, time3, time1);  // cree un tarif plein (id1) et un tarif creux (id2)




        } catch (Exception e){
            e.printStackTrace();
        }

    }
}