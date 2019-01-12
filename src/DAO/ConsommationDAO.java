package DAO;

import MODELE.Compteur;
import MODELE.Consommation;
import MODELE.Tarif;
import MODELE.TarifCreux;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;


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

    public static List<Consommation> getConsommationsByDate(EntityManager em, LocalDate date) {
        String hql = "select c from Consommation c where c.date = :d";
        return em.createQuery(hql).setParameter("d", date).getResultList();
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

    /**
     * Compute the cost of a consommation at a given date.
     * @param em the EntityManager
     * @param c the consommation
     * @param date the date
     * @return the cost
     */
    public static double computeCost(EntityManager em, Consommation c, LocalDate date) {
        double cost = 0;
        List<Tarif> tarifsConso = TarifDAO.getTarifsByConsommation(em, c);
        for(Tarif t : tarifsConso) {
            //Compute the cost of t
            if(c.getHeureDeb().isBefore(t.getTarifPlein().getHeureDeb())
            && c.getHeureArr().isBefore(t.getTarifCreux().getHeureDeb())) {
                //Que du tarif creux sur la periode de conso AVANT la periode pleine
            }
            else if(c.getHeureDeb().isAfter(t.getTarifPlein().getHeureFin())
            && c.getHeureArr().isAfter(t.getTarifPlein().getHeureFin())) {
                //Que du tarif creux sur le periode de conso APRES la periode pleine
            }
            else if(c.getHeureDeb().isBefore(t.getTarifPlein().getHeureDeb())
                    && (c.getHeureArr().isAfter(t.getTarifPlein().getHeureDeb())
                        && c.getHeureArr().isBefore(t.getTarifPlein().getHeureFin()))) {
                //La periode de conso commence en tarif creux et fini pdt la periode pleine
            }
            else if((c.getHeureDeb().isAfter(t.getTarifPlein().getHeureDeb())
                    && c.getHeureDeb().isBefore(t.getTarifPlein().getHeureFin()))
                    && c.getHeureArr().isAfter(t.getTarifPlein().getHeureFin())) {
                //La periode de conso commence en tarif plein et fini après la periode creuse
            }
            else {
                //Que du tarif plein sur la periode de conso
            }
        }
        return cost;
    }

    //TODO utile ?
    public static List<Consommation> getConsommationsByTarifCreux(EntityManager em, TarifCreux tc) {
        String hql = "select c from Consommation c join c.tarifs t join t.tarifCreux tc where tc = :tarifcreux";
        return em.createQuery(hql).setParameter("tarifcreux", tc).getResultList();
    }


}
