package DAO;

import MODELE.Adresse;
import MODELE.Compteur;
import MODELE.Personne;

import javax.persistence.EntityManager;

public class AdresseDAO {

    /**
     * Cree une adresse sans compteur ni personne dans la base de donnees
     * @param em the EntityManager
     * @param rue the rue
     * @param ville the ville
     * @return the new adresse
     */
    public static Adresse createAdresse(EntityManager em, String rue, String ville) {
        Adresse a = new Adresse();
        a.setRue(rue);
        a.setVille(ville);
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        return a;
    }

    /**
     * Met a jour l'attribut personne de l'adresse et l'attribut adresse de personne dans la base de donnees
     * @param em the EntityManager
     * @param a the adresse to update
     * @param p the personne to update
     * @return the updated adresse
     */
    public static Adresse updatePersonne(EntityManager em, Adresse a, Personne p) {
        a.setPersonne(p);
        p.setAdresse(a);
        em.getTransaction().begin();
        em.persist(a);
        em.persist(p);
        em.getTransaction().commit();
        return a;
    }

    /**
     * Met a jour l'attribut compteur de l'adresse et l'attribut adresse de compteur dans la base de donnees
     * @param em the EntityManager
     * @param a the adresse to update
     * @param c the compteur to update
     * @return the updated adresse
     */
    public static Adresse updateCompteur(EntityManager em, Adresse a, Compteur c) {
        a.setCompteur(c);
        c.setAdresse(a);
        em.getTransaction().begin();
        em.persist(a);
        em.persist(c);
        em.getTransaction().commit();
        return a;
    }

    /**
     * Supprime l'adresse du compteur et de la personne associes
     * @param em the EntityManager
     * @param a the adresse to remove
     */
    public static void removeAdresse(EntityManager em, Adresse a) {
        Personne p1 = a.getPersonne();
        p1.setAdresse(null);
        Compteur c1 = a.getCompteur();
        c1.setAdresse(null);

        em.getTransaction().begin();
        em.remove(a);
        em.getTransaction().commit();
    }
}
