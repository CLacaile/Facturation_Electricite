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
     * Crée un nouveau compteur à une adresse existante. Elle affecte à l'attribut personne du nouveau compteur la personne
     * résidant à l'adresse et affecte à l'attribut compteur le nouveau compteur ainsi créé. Si la personne n'existe pas,
     * la création est annulée. Utilise AdresseDAO.updatePersonne() et AdresseDAO.updateCompteur().
     * @param em
     * @param dateActivation
     * @param adresse
     * @return the created compteur
     * @throws Exception si personne n'habite à l'adresse spécifiée
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
     * Met à jour l'attribut personne d'un compteur. Met également à jour les attributs compteur et adresse d'une personne
     * pour lui donner la même adresse que celle du compteur.
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

    /**
     * Ajoute une consommation à un compteur.
     * @param em
     * @param compteur
     * @param conso
     * @return
     */
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
     * Met à jour l'attribut consommation d'un compteur et l'attribut compteur d'une consommation dans la base de données.
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
     * Supprime le compteur de la personne et de la consommation associées.
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
     * Calcule le coût total des consommations d'un compteur
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
