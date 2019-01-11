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
    protected static TarifPlein createTarifCreux(EntityManager em, double prix, LocalTime heureDeb, LocalTime heureFin) {
        TarifPlein tarif = new TarifPlein();
        tarif.setPrix(prix);
        tarif.setHeureDeb(heureDeb);
        tarif.setHeureFin(heureFin);
        em.getTransaction().begin();
        em.persist(tarif);
        em.getTransaction().commit();
        return tarif;
    }

    public static TarifPlein updateHoraires(EntityManager em, TarifPlein tarifToUpdate, Horaires horairesToUpdate) {

        return tarifToUpdate;
    }
}
