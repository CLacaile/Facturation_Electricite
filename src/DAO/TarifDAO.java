package DAO;

import MODELE.Horaires;
import MODELE.Tarif;
import MODELE.TarifCreux;
import MODELE.TarifPlein;

import javax.persistence.EntityManager;
import java.time.LocalTime;
import java.util.List;

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
     * Create a tarif with a tarif creux and a tarif plein with a prix/kWh and a reduction for tarif creux
     * in the database. It also defines a
     * @param em the EntityManager
     * @param prix the prix/kWh of the tarif
     * @param reduction the reduction between 0 and 1 (ex: 2O% of reduction applied to 1€ = 0.8€)
     * @param heureDebCreux the beginning of the heure creux
     * @param heureFinCreux the end of the heure creux
     * @return the new tarif plein
     */
    public static Tarif createTarif(EntityManager em, double prix, double reduction, LocalTime heureDebCreux, LocalTime heureFinCreux){
        // The tarif
        Tarif tarif = new Tarif();
        tarif.setPrix(prix);
        tarif.setReduction(reduction);

        // The tarif plein
        TarifPlein tarifPlein = new TarifPlein();
        tarifPlein.setPrix(prix);
        tarifPlein.setHeureDeb(heureFinCreux);
        tarifPlein.setHeureFin(heureDebCreux);
        tarifPlein.setTarif(tarif);

        // The tarif creux
        TarifCreux tarifCreux = new TarifCreux();
        tarifCreux.setPrix(prix*(1-reduction));
        tarifCreux.setHeureDeb(heureDebCreux);
        tarifCreux.setHeureFin(heureFinCreux);
        tarifCreux.setTarif(tarif);

        // Commit to DB
        TarifCreuxDAO.updateTarifCreux(em, tarifCreux, tarif);
        TarifPleinDAO.updateTarifPlein(em, tarifPlein, tarif);
        return tarif;
    }

    /**
     * Removes the tarif from the tarif creux, tarif plein and horaires associated
     * @param em the EntityManager
     * @param t the tarif to remove
     */
    public static void removeTarif(EntityManager em, Tarif t) {
        TarifCreux tc = t.getTarifCreux();
        tc.setTarif(null);
        TarifPlein tp = t.getTarifPlein();
        tp.setTarif(null);
        List<Horaires> h1 = t.getHoraires();
        for (Horaires h : h1) {
            h1.remove(h);
        }
        t.setHoraires(null);

        em.getTransaction().begin();
        em.remove(t);
        em.getTransaction().commit();
    }

}
