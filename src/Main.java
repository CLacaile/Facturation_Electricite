import CONTROLEUR.Controleur;
import VUE.Vue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bdd_hibernate");
        EntityManager em = emf.createEntityManager();
        Vue vue = new Vue();
        Controleur controleur = new Controleur(em, vue);
        controleur.populateDB();
        if(controleur.execute())
            System.exit(0);
        else
            System.exit(100);
    }

}