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
     * Trouver une consommation dans la base de données
     * @param em l'EntityManager
     * @param id l'id de la consommation
     * @return la consommation
     */
    public static Consommation find(EntityManager em, long id) {
        return (Consommation) em.find(Consommation.class, id);
    }

    /**
     * Créer une consommation associé à un compteur mais sans horaires dans la base de données.
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
     * Compute the cost of a consommation for every tarif of the consommation.
     * @param em the EntityManager
     * @param c the consommation
     * @return the cost
     */
    public static double computeCost(EntityManager em, Consommation c) {
        double cost = 0;
        List<Tarif> tarifsConso = TarifDAO.getTarifsByConsommation(em, c);
        for(Tarif t : tarifsConso) {
            //Compute the cost of t
            if((c.getHeureDeb().isBefore(t.getTarifPlein().getHeureDeb())
                    && c.getHeureArr().isBefore(t.getTarifPlein().getHeureDeb()))
            || (c.getHeureDeb().isAfter(t.getTarifPlein().getHeureFin())
                    && c.getHeureArr().isAfter(t.getTarifPlein().getHeureFin()))) {
                //Que du tarif creux sur la periode de conso AVANT ou APRES la periode pleine
                cost += c.getPuissance() * t.getTarifCreux().getPrix(); // puissance consommée (kWh) * prix/kWh
            }
            else if(c.getHeureDeb().isBefore(t.getTarifPlein().getHeureDeb())
                    && (c.getHeureArr().isAfter(t.getTarifPlein().getHeureDeb())
                        && c.getHeureArr().isBefore(t.getTarifPlein().getHeureFin()))) {
                //La periode de conso commence en tarif creux et fini pdt la periode pleine
                long minutesCreux = c.getHeureDeb().until(t.getTarifPlein().getHeureDeb(), MINUTES);
                long minutesPlein = t.getTarifPlein().getHeureDeb().until(c.getHeureArr(), MINUTES);
                double puissParMinutes = ((double) c.getPuissance())/(minutesCreux+minutesPlein);
                double puissCreux = puissParMinutes*minutesCreux;
                double puissPlein = puissParMinutes*minutesPlein;
                cost += puissCreux*t.getTarifCreux().getPrix() + puissPlein*t.getTarifPlein().getPrix();
            }
            else if((c.getHeureDeb().isAfter(t.getTarifPlein().getHeureDeb())
                    && c.getHeureDeb().isBefore(t.getTarifPlein().getHeureFin()))
                    && c.getHeureArr().isAfter(t.getTarifPlein().getHeureFin())) {
                //La periode de conso commence en tarif plein et fini après la periode creuse
                long minutesPlein = c.getHeureDeb().until(t.getTarifPlein().getHeureFin(), MINUTES);
                long minutesCreux = t.getTarifPlein().getHeureFin().until(c.getHeureArr(), MINUTES);
                double puissParMinutes = ((double) c.getPuissance())/(minutesCreux+minutesPlein);
                double puissCreux = puissParMinutes*minutesCreux;
                double puissPlein = puissParMinutes*minutesPlein;
                cost += puissCreux*t.getTarifCreux().getPrix() + puissPlein*t.getTarifPlein().getPrix();
            }
            else if((c.getHeureDeb().isBefore(t.getTarifPlein().getHeureDeb()))
                    && c.getHeureArr().isAfter(t.getTarifPlein().getHeureFin())) {
                // La periode commence avant la periode pleine et fini après la periode pleine
                long minutesCreux = c.getHeureDeb().until(t.getTarifPlein().getHeureDeb(), MINUTES)
                        + t.getTarifPlein().getHeureFin().until(c.getHeureArr(), MINUTES);
                long minutesPlein = t.getTarifPlein().getHeureDeb().until(t.getTarifPlein().getHeureFin(), MINUTES);
                double puissParMinutes = ((double) c.getPuissance())/(minutesCreux+minutesPlein);
                double puissCreux = puissParMinutes*minutesCreux;
                double puissPlein = puissParMinutes*minutesPlein;
                cost += puissCreux*t.getTarifCreux().getPrix() + puissPlein*t.getTarifPlein().getPrix();
            }
            else {
                //Que du tarif plein sur la periode de conso
                cost += c.getPuissance() * t.getTarifPlein().getPrix();
            }
        }
        return cost;
    }

    /**
     * Retrieve from the db a list of consommation where the tarif t is applied
     * @param em the EntityManager
     * @param t the tarif t
     * @return a list of consommation where t is applied
     */
    public static List<Consommation> getConsommationsByTarif(EntityManager em, Tarif t) {
        String hql = "select c from Consommation c join c.tarifs t where t = :tarif";
        return em.createQuery(hql).setParameter("tarif", t).getResultList();
    }

    /**
     * Retrieve from the db a list of consommation where the tarifCreux tc is applied
     * @param em the EntityManager
     * @param tc the tarifCreux
     * @return a list of consommation where tc is applied
     */
    public static List<Consommation> getConsommationsByTarifCreux(EntityManager em, TarifCreux tc) {
        Tarif tarif = TarifDAO.getTarifByTarifCreux(em, tc);
        return getConsommationsByTarif(em, tarif);
    }

    /**
     * Retrieve from the db the sum of consommations for a given compteur, and a given date
     * @param em the EntityManager
     * @param ct the compteur
     * @param date the date
     * @return the total cost of the consommations by compteur and date
     */
    public static List<Consommation> getConsommationsByCompteurDate(EntityManager em, Compteur ct, LocalDate date) {
        String hql = "select c from Consommation c join c.compteur ct where c.date = :d";
        return em.createQuery(hql).setParameter("d", date).getResultList();
    }

}
