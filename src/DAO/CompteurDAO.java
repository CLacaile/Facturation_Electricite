package DAO;
import MODELE.Adresse;
import MODELE.Compteur;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class CompteurDAO {
    public static Compteur createCompteur(EntityManager em, LocalDate dateActivation, String rue, String ville) {
        Adresse a = new Adresse();
        a.setRue(rue);
        a.setVille(ville);
        Compteur compteur = new Compteur();
        compteur.setDate(dateActivation);
        compteur.setAdresse(a);
        a.setCompteur(compteur);

        em.getTransaction().begin();
        em.persist(compteur);
        em.getTransaction().commit();
        return compteur;
    }
}
