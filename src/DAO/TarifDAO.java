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

    /**
     * Create a tarif with a prix/kWh in the database.
     * @param em the EntityManager
     * @param prix the prix/kWh of the tarif
     * @return the new tarif
     */
    public static Tarif createTarif(EntityManager em, double prix){
        Tarif tarif = new Tarif();
        tarif.setPrix(prix);
        em.getTransaction().begin();
        em.persist(tarif);
        em.getTransaction().commit();
        return tarif;
    }
}
