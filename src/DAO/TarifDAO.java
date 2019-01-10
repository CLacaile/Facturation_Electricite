package DAO;

import MODELE.Tarif;

import javax.persistence.EntityManager;

public class TarifDAO {

    /**
     * Find a tarif in the DB
     * @param em the EntityManager
     * @param id the id
     * @return the Tarif or null if it doesn't exist
     */
    public static Tarif find(EntityManager em, long id) {
        return (Tarif) em.find(Tarif.class, id);
    }
}
