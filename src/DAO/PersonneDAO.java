package DAO;

import MODELE.Adresse;
import MODELE.Compteur;
import MODELE.Personne;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class PersonneDAO {

    /**
     * Trouve une personne dans la base de données.
     * @param em the EntityManager
     * @param numSS the numero de securite sociale
     * @return the personne
     */
    public static Personne find(EntityManager em, long numSS) {
        return (Personne) em.find(Personne.class, numSS);
    }

    /**
     * Crée une personne sans compteur ni adresse dans la base de données. Utilise updateAdresse et updateCompteur.
     * @param em
     * @param numSS
     * @param numTel
     * @return
     */
    public static Personne createPersonne(EntityManager em, long numSS, String numTel) {
        Personne pers = new Personne();
        pers.setNumSS(numSS);
        pers.setNumTel(numTel);

        em.getTransaction().begin();
        em.persist(pers);
        em.getTransaction().commit();
        return pers;
    }

    /**
     * Met à jour l'attribut adresse de la personne et l'attribut personne de l'adresse dans la base de données. Utilise AdresseDAO#updatePersonne().
     * @param em the EntityManager
     * @param p the personne to update
     * @param a the adresse to update
     * @return the updated personne
     * @see AdresseDAO#updatePersonne(EntityManager, Adresse, Personne)
     */
    public static Personne updateAdresse(EntityManager em, Personne p, Adresse a) {
        AdresseDAO.updatePersonne(em, a, p);
        return p;
    }

    /**
     * Met à jour l'attribut compteur de la personne et l'attribut personne attribute du compteur dans la base de données. Utilise CompteurDAO#updatePersonne().
     * @param em the EntityManager
     * @param p the personne to update
     * @param c the compteur to update
     * @return the updated personne
     * @see CompteurDAO#updatePersonne(EntityManager, Compteur, Personne)
     */
    public static Personne updateCompteur(EntityManager em, Personne p, Compteur c) {
        CompteurDAO.updatePersonne(em, c, p);
        return p;
    }

    /**
     * Supprime une personne de l'adresse et du compteur associés.
     * @param em the EntityManager
     * @param p the personne to remove
     */
    public static void removePersonne(EntityManager em, Personne p) {
        Adresse a1 = p.getAdresse();
        a1.setPersonne(null);
        Compteur c1 = p.getCompteur();
        c1.setPersonne(null);

        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }
}
