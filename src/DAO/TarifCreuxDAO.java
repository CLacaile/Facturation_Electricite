package DAO;

import MODELE.TarifCreux;

import javax.persistence.EntityManager;

public class TarifCreuxDAO extends TarifDAO {

    public static TarifCreux createTarifCreux(EntityManager em, double reduction){
        TarifCreux tarifCreux = new TarifCreux();
        tarifCreux.setReduction(reduction);
        return tarifCreux;
    }
}
