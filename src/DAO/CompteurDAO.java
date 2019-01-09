package DAO;
import MODELE.Adresse;
import MODELE.Compteur;
import MODELE.Personne;

import javax.persistence.EntityManager;
import java.time.LocalDate;


public class CompteurDAO {
    /**
     * Create a new compteur at an existing adresse. It sets the personne attribute of the new compteur as the personne
     * at the specified adresse.
     * @param em
     * @param dateActivation
     * @param adresse
     * @return the created compteur
     */
    public static Compteur createCompteur(EntityManager em, LocalDate dateActivation, Adresse adresse) {
        Compteur compteur = new Compteur();
        compteur.setDate(dateActivation);
        compteur.setAdresse(adresse);
        compteur.setPersonne(adresse.getPersonne());
        adresse.setCompteur(compteur);

        em.getTransaction().begin();
        em.persist(adresse);
        em.persist(compteur);
        em.getTransaction().commit();
        return compteur;
    }

    /**
     * Create a new compteur and a new adresse in the database with no personne associated !
     * @param em
     * @param dateActivation
     * @param rue
     * @param ville
     * @return the created compteur
     */
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

    /**
     * Create a compteur for an existing personne. It uses the adresse of the personne as adresse for the new compteur
     * @param em
     * @param dateActivation
     * @param personne
     * @return the created compteur
     */
    public static Compteur createCompteur(EntityManager em, LocalDate dateActivation, Personne personne) {
        Compteur compteur = new Compteur();
        compteur.setDate(dateActivation);
        compteur.setPersonne(personne);
        Adresse adresse = personne.getAdresse();
        compteur.setAdresse(adresse);
        adresse.setCompteur(compteur);

        em.getTransaction().begin();
        em.persist(adresse);
        em.persist(compteur);
        em.getTransaction().commit();
        return compteur;
    }
}
