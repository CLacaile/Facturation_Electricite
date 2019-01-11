package DAO;

import MODELE.Horaires;
import MODELE.Tarif;
import MODELE.TarifPlein;

import javax.persistence.EntityManager;
import java.time.LocalTime;

public class TarifPleinDAO {

    /**
     * Create a tarif plein in the db
     * @param em the EntityManager
     * @param prix the prix of the tarif plein
     * @param heureDeb the time of beggining of the plein period (should be the time of end of the creux period)
     * @param heureFin the time of end of the plein period (should be the time of beggining of the creux period)
     * @return the new tarif plein
     */
    protected static TarifPlein createTarifPlein(EntityManager em, double prix, LocalTime heureDeb, LocalTime heureFin) {
        TarifPlein tarif = new TarifPlein();
        tarif.setPrix(prix);
        tarif.setHeureDeb(heureDeb);
        tarif.setHeureFin(heureFin);
        em.getTransaction().begin();
        em.persist(tarif);
        em.getTransaction().commit();
        return tarif;
    }

    /**
     * Update a tarif attibute of the tarif plein and the tarif plein attribute of a tarif in the db
     * @param em
     * @param tarifCreuxToUpdate
     * @param tarifToUpdate
     * @return
     */
    public static TarifPlein updateTarifPlein(EntityManager em, TarifPlein tarifPleinToUpdate, Tarif tarifToUpdate) {
        tarifPleinToUpdate.setTarif(tarifToUpdate);
        tarifToUpdate.setTarifPlein(tarifPleinToUpdate);
        em.getTransaction().begin();
        em.persist(tarifPleinToUpdate);
        em.persist(tarifToUpdate);
        em.getTransaction().commit();
        return tarifPleinToUpdate;
    }

    /**
     * Removes the tarif plein from the tarif associated
     * @param em the EntityManager
     * @param tp the tarif plein to remove
     */
    public static void removeTarifCreux(EntityManager em, TarifPlein tp) {
        Tarif t1 = tp.getTarif();
        t1.setTarifCreux(null);

        em.getTransaction().begin();
        em.remove(tp);
        em.getTransaction().commit();
    }
}
