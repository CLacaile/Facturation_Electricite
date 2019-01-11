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
        Tarif tarif = new Tarif();
        tarif.setReduction(reduction);

        TarifPlein tarifPlein = new TarifPlein();
        tarifPlein.setPrix(prix);

        tarifPlein.setHeureDeb(heureFinCreux);
        tarifPlein.setHeureFin(heureDebCreux);
        tarifPlein.setTarif(tarif);

        TarifCreux tarifCreux = new TarifCreux();
        tarifCreux.setPrix(prix*(1-reduction));
        tarifCreux.setHeureDeb(heureDebCreux);
        tarifCreux.setHeureFin(heureFinCreux);
        tarifCreux.setTarif(tarif);

        tarif.setTarifCreux(tarifCreux);
        tarif.setTarifPlein(tarifPlein);

        em.getTransaction().begin();
        em.persist(tarifPlein);
        em.persist(tarifCreux);
        em.persist(tarif);
        em.getTransaction().commit();
        return tarif;
    }
}
