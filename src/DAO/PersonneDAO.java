package DAO;

import MODELE.Adresse;
import MODELE.Compteur;
import MODELE.Personne;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class PersonneDAO {
    public static Personne createPersonne(EntityManager em, long numSS, Compteur compteur, Adresse adresse, String numTel) {
        Personne pers = new Personne();
        pers.setNumSS(numSS);
        pers.setNumTel(numTel);
        pers.setAdresse(adresse);
        pers.setCompteur(compteur);
        compteur.setPersonne(pers);

        em.getTransaction().begin();
        em.persist(pers);
        em.persist(compteur);
        em.getTransaction().commit();
        return pers;
    }
}
