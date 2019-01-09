package DAO;

import MODELE.Compteur;
import MODELE.Consommation;
import MODELE.Horaires;

import javax.persistence.EntityManager;
import java.util.List;

public class ConsommationDAO {
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
     * Update the list of horaires of a consommation
     * @param em the EntityManager
     * @param c the consommation to update
     * @param horaires the list of horaires to update
     * @return the updated consommation
     */
    public static Consommation updateHoraires(EntityManager em, Consommation c, List<Horaires> horaires) {
        c.setHoraires(horaires);
        for(Horaires h : horaires) {
            h.setConsommation(c);
        }
        em.getTransaction().begin();
        em.persist(c);
        em.persist(horaires);
        return c;
    }

    /**
     * Find a Consommation in the DB
     * @param em the EntityManager
     * @param id the id of the consommation
     * @return the consommation
     */
    public static Consommation find(EntityManager em, long id) {
        return (Consommation) em.find(Consommation.class, id);
    }


}
