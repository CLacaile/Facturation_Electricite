package DAO;

import MODELE.Horaires;
import MODELE.Tarif;
import MODELE.TarifCreux;

import javax.persistence.EntityManager;

public class TarifCreuxDAO extends TarifDAO {

    public static TarifCreux updateHoraires(EntityManager em, TarifCreux tarifToUpdate, Horaires horairesToUpdate) {
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
