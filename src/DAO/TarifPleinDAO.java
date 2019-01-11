package DAO;

import MODELE.Horaires;
import MODELE.Tarif;
import MODELE.TarifPlein;

import javax.persistence.EntityManager;

public class TarifPleinDAO {

    public static TarifPlein updateHoraires(EntityManager em, TarifPlein tarifToUpdate, Horaires horairesToUpdate) {
        Horaires previousHoraires = tarifToUpdate.getHoraires();
        if(previousHoraires == null) {
            // if the tarif has just been created
            tarifToUpdate.setHoraires(horairesToUpdate);
            horairesToUpdate.getTarifs().add(tarifToUpdate);
        }
        else if(previousHoraires != horairesToUpdate) {
            // if the tarif already exists in an other horaires
            previousHoraires.getTarifs().remove(tarifToUpdate);
            tarifToUpdate.setHoraires(horairesToUpdate);
            horairesToUpdate.getTarifs().add(tarifToUpdate);
        }
        em.getTransaction().begin();
        em.persist(tarifToUpdate);
        em.persist(horairesToUpdate);
        em.persist(previousHoraires);
        em.getTransaction().commit();
        return tarifToUpdate;
    }
}
