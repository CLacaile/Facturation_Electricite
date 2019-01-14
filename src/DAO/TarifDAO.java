package DAO;

import MODELE.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalTime;
import java.util.List;

public class TarifDAO {

    /**
     * Trouve un tarif dans la base de données.
     * @param em the EntityManager
     * @param id the id
     * @return the Tarif or null if it doesn't exist
     */
    public static Tarif find(EntityManager em, long id) {
        return (Tarif) em.find(Tarif.class, id);
    }

    /**
     * Crée un tarif avec un tarif creux, un tarif plein, un prix/kWh et une reduction pour le tarif creux dans la base de données.
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
     * Ajoute une consommation à la liste des consommations d'un tarif et un tarif à la liste des tarifs d'une consommation.
     * @param em the EntityManager
     * @param t the Tarif
     * @param c the consommation to add
     * @return the Tarif
     * @throws Exception if the tarif is already in the horaires list or if the horaire is already in the tarif list
     */
    public static Tarif addConsommation(EntityManager em, Tarif t, Consommation c) throws Exception {
        if(t.getConsommations().contains(c) == false) {
            // t ne contient pas h
            t.getConsommations().add(c);
        }
        else {
            System.out.println("Le tarif possede deja cet horaire. Abandon.");
            throw new Exception();
        }
        if(c.getTarifs().contains(t) == false) {
            // h ne contient pas t
            c.getTarifs().add(t);
        }
        else {
            System.out.println("La conso connait deja ce tarif. Abandon.");
            throw new Exception();
        }
        em.getTransaction().begin();
        em.persist(t);
        em.persist(c);
        em.getTransaction().commit();
        return t;
    }

    /**
     * Donne tous les tarifs de la base de données.
     * @param em the EntityManager
     * @return the tarifs of the db
     */
    public static List<Tarif> getAllTarifs(EntityManager em) {
        Query query = em.createQuery("select t from Tarif t");
        return query.getResultList();
    }

    /**
     * Donne le tarif d'un tarif creux
     * @param em
     * @param tc
     * @return
     */
    public static Tarif getTarifByTarifCreux(EntityManager em, TarifCreux tc) {
        String hql = "select t from TarifCreux tc join tc.tarif t where tc =:tarifCreux";
        return (Tarif) em.createQuery(hql).setParameter("tarifCreux", tc).getSingleResult();

    }

    /**
     * Donne la liste des tarifs d'une consommation.
     * @param em
     * @param c
     * @return
     */
    public static List<Tarif> getTarifsByConsommation(EntityManager em, Consommation c) {
        String hql = "select distinct t from Tarif t join t.consommations c where c = :conso";
        return em.createQuery(hql).setParameter("conso", c).getResultList();
    }

    /**
     * Supprime le tarif des tarif creux, tarif plein et consommation associés.
     * @param em the EntityManager
     * @param t the tarif to remove
     */
    public static void removeTarif(EntityManager em, Tarif t) {
        TarifCreux tc = t.getTarifCreux();
        tc.setTarif(null);
        TarifPlein tp = t.getTarifPlein();
        tp.setTarif(null);
        List<Consommation> c1 = t.getConsommations();
        for (Consommation c : c1) {
            c1.remove(c);
        }
        t.setConsommations(null);

        em.getTransaction().begin();
        em.remove(t);
        em.remove(tc);
        em.remove(tp);
        em.getTransaction().commit();
    }
}
