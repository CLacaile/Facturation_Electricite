package DAO;

import MODELE.Adresse;

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
}
