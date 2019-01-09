import DAO.CompteurDAO;
import MODELE.Adresse;
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

    }
}