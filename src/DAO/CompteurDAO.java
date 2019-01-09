package DAO;
import MODELE.Adresse;
import MODELE.Compteur;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class CompteurDAO {
    public static Compteur createCompteur(EntityManager em, LocalDate dateActivation, Adresse adresse) {
        Compteur compteur = new Compteur();
        compteur.setDate(dateActivation);
        compteur.setAdresse(adresse);
        adresse.setCompteur(compteur);

        em.getTransaction().begin();
        em.persist(compteur);
        em.getTransaction().commit();
        return compteur;
    }

    public static Compteur createCompteur(EntityManager em, LocalDate dateActivation, String rue, String ville) {
        Compteur compteur = new Compteur();
        compteur.setDate(dateActivation);
        Adresse adresse = new Adresse();
        adresse.setVille(ville);
        adresse.setRue(rue);
        compteur.setAdresse(adresse);
        adresse.setCompteur(compteur);

        em.getTransaction().begin();
        em.persist(adresse);
        em.persist(compteur);
        em.getTransaction().commit();
        return compteur;
    }
}
