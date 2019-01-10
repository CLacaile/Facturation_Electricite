package DAO;

import MODELE.Compteur;
import MODELE.Consommation;
import MODELE.Horaires;

import javax.persistence.EntityManager;
import java.util.List;

public class ConsommationDAO {
    /**
     * Find a Consommation in the DB
     * @param em the EntityManager
     * @param id the id of the consommation
     * @return the consommation
     */
    public static Consommation find(EntityManager em, long id) {
        return (Consommation) em.find(Consommation.class, id);
    }

    /**
     * Create a consommation without horaires but with a compteur in the DB. Use updateHoraires to set the horaires
     * @param em
     * @return the new consommation associated to a compteur
     * @see CompteurDAO#updateConsommation(EntityManager, Compteur, Consommation)
     */
    public static Consommation createConsommation(EntityManager em, Compteur c) {
        Consommation consommation = new Consommation();
        CompteurDAO.updateConsommation(em, c, consommation);
        return consommation;
    }

    /**
     * Add an horaires to the list of horaires in the DB.
     * @param em the EntityManager
     * @param c the consommation
     * @param h the horaires
     * @return the updated consommation
     */
    public static Consommation addHoraires(EntityManager em, Consommation c, Horaires h) throws IllegalArgumentException {
        if(c.getHoraires() == null) {
            System.out.println("Aucune liste d'horaires n'a ete creee. Abandon.");
            throw new IllegalArgumentException();
        }
        c.getHoraires().add(h);
        h.setConsommation(c);
        em.getTransaction().begin();
        em.persist(c);
        em.persist(h);
        em.getTransaction().commit();
        return c;
    }




}
