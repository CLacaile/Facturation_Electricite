package DAO;

import MODELE.Tarif;
import MODELE.TarifCreux;
import net.bytebuddy.asm.Advice;

import javax.persistence.EntityManager;
import java.time.LocalTime;

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

}
