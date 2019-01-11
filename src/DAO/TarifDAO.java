package DAO;

import MODELE.Horaires;
import MODELE.Tarif;
import MODELE.TarifCreux;
import MODELE.TarifPlein;

import javax.persistence.EntityManager;
import java.time.LocalTime;

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
     * Create a tarif creux and a tarif plein with a prix/kWh and a reduction for tarif creux in the database. It also
     * defines a
     * @param em the EntityManager
     * @param prix the prix/kWh of the tarif
     * @param reduction the reduction between 0 and 1 (ex: 2O% of reduction applied to 1€ = 0.8€)
     * @param heureDebCreux the beginning of the heure creux
     * @param heureFinCreux the end of the heure creux
     * @return the new tarif plein
     */
    public static Tarif createTarif(EntityManager em, double prix, double reduction, LocalTime heureDebCreux, LocalTime heureFinCreux){
        Tarif tarifPlein = new TarifPlein();
        tarifPlein.setPrix(prix);
        ((TarifPlein) tarifPlein).setHeureDeb(heureFinCreux);
        ((TarifPlein) tarifPlein).setHeureFin(heureDebCreux);

        Tarif tarifCreux = new TarifCreux();
        ((TarifCreux) tarifCreux).setReduction(reduction);
        tarifCreux.setPrix(prix*(1-reduction));
        ((TarifCreux) tarifCreux).setHeureDeb(heureDebCreux);
        ((TarifCreux) tarifCreux).setHeureFin(heureFinCreux);

        em.getTransaction().begin();
        em.persist(tarifPlein);
        em.persist(tarifCreux);
        em.getTransaction().commit();
        return tarifPlein;
    }

    /**
     * Update the horaires attribute of a tarif and the tarif attribute of a horaires in the DB. Uses
     * TarifCreuxDAO.updateHoraires and TarifPleinDAO.updateHoraires
     * @param em
     * @param tarifToUpdate
     * @param horairesToUpdate
     * @see TarifPleinDAO#updateHoraires(EntityManager, TarifPlein, Horaires)
     * @see TarifCreuxDAO#updateHoraires(EntityManager, TarifCreux, Horaires)
     */
    public static void updateHoraires(EntityManager em, Tarif tarifToUpdate, Horaires horairesToUpdate) {
        if(tarifToUpdate instanceof TarifCreux) {
            TarifCreuxDAO.updateHoraires(em, (TarifCreux) tarifToUpdate, horairesToUpdate);
        }
        else {
            TarifPleinDAO.updateHoraires(em, (TarifPlein) tarifToUpdate, horairesToUpdate);
        }
    }
}
