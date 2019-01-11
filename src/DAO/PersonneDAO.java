package DAO;

import MODELE.Adresse;
import MODELE.Compteur;
import MODELE.Personne;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class PersonneDAO {

    /**
     * Find a personne in the DB
     * @param em the EntityManager
     * @param numSS the numero de securite sociale
     * @return the personne
     */
    public static Personne find(EntityManager em, long numSS) {
        return (Personne) em.find(Personne.class, numSS);
    }

    /**
     * Create a person with no compteur or adresse in the database. To set these values, use updateAdresse and updateCompteur
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
     * Update the adresse attribute of the personne and the personne attribute of the adresse in the DB. Use AdresseDAO#updatePersonne()
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
     * Update the compteur attribute of personne and the personne attribute of compteur in the DB. Use CompteurDAO#updatePersonne()
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
     * Removes the personne from the adresse and compteur associated
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
