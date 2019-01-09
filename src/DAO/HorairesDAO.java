package DAO;

import MODELE.Consommation;
import MODELE.Horaires;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;

public class HorairesDAO {

    /**
     * Find a Horaires in the DB
     * @param em the EntityManager
     * @param id the id of the horaire
     * @return the horaires
     */
    public static Horaires find(EntityManager em, long id) {
        return (Horaires) em.find(Horaires.class, id);
    }

    /**
     * Create an empty horaires with no tarif and no consommation in the DB. Use updateTarif or updateConsommation
     * @param em
     * @param date
     * @param heureDeb
     * @param heureFin
     * @return
     */
    public static Horaires createHoraires(EntityManager em, LocalDate date, LocalTime heureDeb, LocalTime heureFin) {
        Horaires h = new Horaires();
        h.setDate(date);
        h.setHeureDeb(heureDeb);
        h.setHeureArr(heureFin);
        em.getTransaction().begin();
        em.persist(h);
        em.getTransaction().commit();
        return h;
    }

    public static Horaires updateConsommation(EntityManager em, Horaires h, Consommation c) {

        return h;
    }
}
