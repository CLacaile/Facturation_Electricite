package DAO;

import MODELE.Tarif;
import MODELE.TarifCreux;
import net.bytebuddy.asm.Advice;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalTime;
import java.util.List;

public class TarifCreuxDAO {

    /**
     * Create a tarif creux in the db
     * @param em
     * @param prix
     * @param heureDeb
     * @param heureFin
     * @return
     */
    protected static TarifCreux createTarifCreux(EntityManager em, double prix, LocalTime heureDeb, LocalTime heureFin) {
        TarifCreux tarif = new TarifCreux();
        tarif.setPrix(prix);
        tarif.setHeureDeb(heureDeb);
        tarif.setHeureFin(heureFin);
        em.getTransaction().begin();
        em.persist(tarif);
        em.getTransaction().commit();
        return tarif;
    }

    /**
     * Update a tarif attibute of the tarif creux and the tarif creux attribute of a tarif in the db
     * @param em
     * @param tarifCreuxToUpdate
     * @param tarifToUpdate
     * @return
     */
    public static TarifCreux updateTarifCreux(EntityManager em, TarifCreux tarifCreuxToUpdate, Tarif tarifToUpdate) {
        tarifCreuxToUpdate.setTarif(tarifToUpdate);
        tarifToUpdate.setTarifCreux(tarifCreuxToUpdate);
        em.getTransaction().begin();
        em.persist(tarifCreuxToUpdate);
        em.persist(tarifToUpdate);
        em.getTransaction().commit();
        return tarifCreuxToUpdate;
    }

    /**
     * Removes the tarif creux from the tarif associated
     * @param em the EntityManager
     * @param tc the tarif creux to remove
     */
    public static void removeTarifCreux(EntityManager em, TarifCreux tc) {
        Tarif t1 = tc.getTarif();
        t1.setTarifCreux(null);

        em.getTransaction().begin();
        em.remove(tc);
        em.getTransaction().commit();
    }

    public static List<TarifCreux> getAllTarifCreux(EntityManager em) {
        Query q = em.createQuery("select tc from TarifCreux tc");
        return q.getResultList();
    }

    public static List<TarifCreux> getTarifsCreuxByTarif(EntityManager em, Tarif t) {
        String hql = "select tc from TarifCreux tc where tc.tarif = :tarif";
        return em.createQuery(hql).setParameter("tarif", t).getResultList();
    }

}
