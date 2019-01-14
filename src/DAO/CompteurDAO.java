package DAO;
import MODELE.Adresse;
import MODELE.Compteur;
import MODELE.Consommation;
import MODELE.Personne;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;


// PAS DE UPDATE ADRESSE CAR ON NE PEUT PAS DEPLACER UN COMPTEUR D'UNE HABITATION A UNE AUTRE.
// DONC LE CREATE DOIT FORCEMENT PRENDRE EN PARAMETRE UNE ADRESSE
public class CompteurDAO {

    /**
     * Create a new compteur at an existing adresse. It sets the personne attribute of the new compteur as the personne
     * at the specified adresse and the compteur of personne as the newly created compteur. If the personne doesn't exist,
     * the creation is aborted. Uses AdresseDAO.updatePersonne() and AdresseDAO.updateCompteur().
     * @param em
     * @param dateActivation
     * @param adresse
     * @return the created compteur
     * @see AdresseDAO#updateCompteur(EntityManager, Adresse, Compteur)
     * @see AdresseDAO#updateCompteur(EntityManager, Adresse, Compteur)
     */
    public static Compteur createCompteur(EntityManager em, LocalDate dateActivation, Adresse adresse) throws Exception {
        Compteur compteur = new Compteur();
        compteur.setDate(dateActivation);
        Personne personne = adresse.getPersonne();
        if(personne == null) {
            System.out.println("Personne n'habite a l'adresse existante. Abandon de la creation.");
            throw new Exception();
        }
        compteur.setPersonne(personne);
        AdresseDAO.updatePersonne(em, adresse, personne);
        AdresseDAO.updateCompteur(em, adresse, compteur);
        return compteur;
    }

    public static Compteur find(EntityManager em, long id) {
        return (Compteur) em.find(Compteur.class, id);
    }

    /**
     * Update the personne attribute of a compteur. It also updates the compteur attributes of a personne and the adres-
     * se attribute of the personne to put the same adresse as the compteur
     * @param em
     * @param compteur the compteur to update
     * @param personne the personne to update
     * @return the updated compteur
     */
    public static Compteur updatePersonne(EntityManager em, Compteur compteur, Personne personne) {
        Adresse adr = compteur.getAdresse();
        compteur.setPersonne(personne);
        personne.setCompteur(compteur);
        personne.setAdresse(adr);
        adr.setPersonne(personne);
        em.getTransaction().begin();
        em.persist(compteur);
        em.persist(personne);
        em.getTransaction().commit();
        return compteur;
    }

    public static Compteur addConsommation(EntityManager em, Compteur compteur, Consommation conso) {
        conso.setCompteur(compteur);
        compteur.getConsommations().add(conso);
        em.getTransaction().begin();
        em.persist(compteur);
        em.persist(conso);
        em.getTransaction().commit();
        return compteur;
    }

    /**
     * Update the consommation attribute of a compteur and updates the compteur attribute of consommation in the DB.
     * @param em the EntityManager
     * @param compteur the compteur to update
     * @param conso the conso to update
     * @return the updated compteur
     */
    public static Compteur updateConsommations(EntityManager em, Compteur compteur, List<Consommation> conso) {
        compteur.setConsommations(conso);
        for(Consommation c : conso) {
            c.setCompteur(compteur);
        }
        em.getTransaction().begin();
        em.persist(compteur);
        em.persist(conso);
        em.getTransaction().commit();
        return compteur;
    }

    /**
     * Removes the compteur from the personne and consommation associated
     * @param em the EntityManager
     * @param c the compteur to remove
     */
    public static void removeCompteur(EntityManager em, Compteur c) {
        Personne p1 = c.getPersonne();
        p1.setCompteur(null);
        List<Consommation> c1 = c.getConsommations();
        for (Consommation cons : c1) {
            cons.setCompteur(null);
        }

        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
    }

    /**
     * Compute the cost of the consommations of a compteur
     * @param em
     * @param c
     * @param date
     * @return
     */
    public static double computeCost(EntityManager em, Compteur c, LocalDate date) {
        double cost = 0;
        List<Consommation> consoList = ConsommationDAO.getConsommationsByCompteurDate(em, c, date);
        for(Consommation cons : consoList) {
            cost += ConsommationDAO.computeCost(em, cons);
        }
        return cost;
    }


}
