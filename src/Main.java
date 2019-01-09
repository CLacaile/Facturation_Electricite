import DAO.AdresseDAO;
import DAO.CompteurDAO;
import DAO.PersonneDAO;
import MODELE.Adresse;
import MODELE.Compteur;
import MODELE.Personne;
import net.bytebuddy.asm.Advice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bdd_hibernate");
        EntityManager em = emf.createEntityManager();

        LocalDate date1 = LocalDate.now();

        // Test
        try {
            Personne p = PersonneDAO.createPersonne(em, 11111, "0606060606");
            Adresse a = AdresseDAO.createAdresse(em, "Parmentier", "Tours");
            p = PersonneDAO.updateAdresse(em, p, a);
            Compteur c = CompteurDAO.createCompteur(em, date1, a);
            CompteurDAO.updatePersonne(em, c, p);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}