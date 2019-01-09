package DAO;
import MODELE.Adresse;
import MODELE.Compteur;
import MODELE.Personne;

import javax.persistence.EntityManager;
import java.time.LocalDate;



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


}
