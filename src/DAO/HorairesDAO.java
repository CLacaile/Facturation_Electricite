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


    /**
     * Updates an horaires element in the list of horaires of consommation. If the horaires must either be a new one or
     * already in consommation. It is not meant to update an horaires in the horaires list of consommation !!
     * @param em
     * @param h
     * @param c
     * @return
     */
    public static Horaires updateConsommation(EntityManager em, Horaires h, Consommation c) {
        //update the horaires element in the list
        if(c.getHoraires().contains(h)) {
            int index = c.getHoraires().indexOf(h);
            c.getHoraires().set(index, h);
            h.setConsommation(c);
        }
        //add the horaires to the list
        else {
            ConsommationDAO.addHoraires(em, c, h);
            return h;
        }

        em.getTransaction().begin();
        em.persist(c);
        em.persist(h);
        em.getTransaction().commit();
        return h;
    }

}
