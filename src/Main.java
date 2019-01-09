import DAO.CompteurDAO;
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


        //Test de création compteur 3 avec une personne existante
        Adresse adr1 = new Adresse();
        adr1.setRue("Parmentier");
        adr1.setVille("Tours");
        Personne p1 = new Personne();
        p1.setNumSS(11111);
        p1.setNumTel("0606060606");
        p1.setAdresse(adr1);
        adr1.setPersonne(p1);
        LocalDate date1 = LocalDate.now();
        CompteurDAO.createCompteur(em, date1, p1);

        //Test de création compteur 1 avec une adresse existante
        Adresse adr3 = new Adresse();
        adr3.setRue("Parmentier");
        adr3.setVille("Tours");
        Personne p2 = new Personne();
        p2.setNumSS(22222);
        p2.setNumTel("0606060606");
        p2.setAdresse(adr3);
        adr3.setPersonne(p2);
        try {
            CompteurDAO.createCompteur(em, date1, adr3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Test de création compteur 2 sans adresse existante ni personne
        Compteur compt = CompteurDAO.createCompteur(em, date1, "Grammont", "Tours");
        Personne p3 = new Personne();
        p3.setNumSS(33333);
        p3.setNumTel("0606060606");
        CompteurDAO.updatePersonne(em, compt, p3);


    }
}