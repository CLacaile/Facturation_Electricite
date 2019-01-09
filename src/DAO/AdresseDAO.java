package DAO;

import MODELE.Adresse;
import MODELE.Personne;

import javax.persistence.EntityManager;

public class AdresseDAO {

    /**
     * Create an adress with no compteur and no personne
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
     * Update the personne attribute of adresse and the adresse attribute of personne
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
}
