package DAO;
import MODELE.Adresse;
import MODELE.Compteur;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class CompteurDAO {
    public Compteur createCompteur(EntityManager em, LocalDate dateActivation, Adresse adresse) {
        Compteur compteur = new Compteur();
        compteur.setDate(dateActivation);
        compteur.setAdresse(adresse);
        return compteur;
    }
}
