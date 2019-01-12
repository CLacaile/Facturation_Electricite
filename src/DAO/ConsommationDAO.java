package DAO;

import MODELE.Compteur;
import MODELE.Consommation;
import MODELE.Tarif;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsommationDAO {
    /**
     * Find a Consommation in the DB
     * @param em the EntityManager
     * @param id the id of the consommation
     * @return the consommation
     */
    public static Consommation find(EntityManager em, long id) {
        return (Consommation) em.find(Consommation.class, id);
    }

    /**
     * Create a consommation without horaires but with a compteur in the DB. Use updateHoraires to set the horaires
     * @param em
     * @return the new consommation associated to a compteur
     * @see CompteurDAO#addConsommation(EntityManager, Compteur, Consommation)
     */
    public static Consommation createConsommation(EntityManager em, LocalDate date, LocalTime heureDeb, LocalTime heureFin,
                                                  int puissance, Compteur compteur) {
        Consommation consommation = new Consommation();
        consommation.setDate(date);
        consommation.setHeureDeb(heureDeb);
        consommation.setHeureArr(heureFin);
        consommation.setPuissance(puissance);
        consommation.setCompteur(compteur);
        CompteurDAO.addConsommation(em, compteur, consommation);
        return consommation;
    }

    /**
     * Add an horaires to the list of horaires in the DB.
     * @param em the EntityManager
     * @param c the consommation
     * @param t the tarif
     * @return the updated consommation
     */
    public static Consommation addTarif(EntityManager em, Consommation c, Tarif t) throws Exception {
        TarifDAO.addConsommation(em, t, c);
        return c;
    }

    /**
     * Removes the consommation from the compteur and horaires associated
     * @param em the EntityManager
     * @param c the consommation to remove
     */
    public static void removeConsommation(EntityManager em, Consommation c) {
        Compteur c1 = c.getCompteur();
        c1.setConsommations(null);
        List<Tarif> t1 = c.getTarifs();
        for (Tarif t : t1) {
            t1.remove(t);
        }
        c.setTarifs(null);

        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
    }


}
