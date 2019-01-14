import CONTROLEUR.Controleur;
import DAO.*;
import MODELE.*;
import VUE.Vue;
import net.bytebuddy.asm.Advice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bdd_hibernate");
        EntityManager em = emf.createEntityManager();
        Vue vue = new Vue();
        Controleur controleur = new Controleur(em, vue);
        controleur.populateDB();
        controleur.execute();
    }

    public static void displayedView(EntityManager em, int numQuestion){
        Scanner sc = new Scanner(System.in);
        String str = "";
        if(numQuestion == 1) {
            // Question 1 : Quelles sont les consommations pour le tarif creux #id?
            System.out.println("Entrez l'identifiant du tarif choisi : ");
            str = sc.nextLine();
            long id = Long.parseLong(str);

            Tarif tarif = TarifDAO.getTarifByTarifCreux(em, TarifCreuxDAO.find(em, id));
            List<Consommation> consoTarif = ConsommationDAO.getConsommationsByTarif(em, tarif);
            for(Consommation c : consoTarif) {
                System.out.println("A la conso #" + c.getId() + " a été appliquée le tarif creux #" + id);
            }

        } else if (numQuestion == 2) {
            // Question 2 : Quel est le coût total des consommations du jour #date pour le compteur #numC?
            System.out.println("Entrez le numéro du compteur choisi : ");
            str = sc.nextLine();
            long numC = Long.parseLong(str);
            System.out.println("Date choisie ");
            System.out.println("Entrez le jour : \n");
            str = sc.nextLine();
            int j = Integer.parseInt(str);
            System.out.println("Entrez le mois : \n");
            str = sc.nextLine();
            int m = Integer.parseInt(str);
            System.out.println("Entrez l'année : \n");
            str = sc.nextLine();
            int a = Integer.parseInt(str);
            LocalDate date = LocalDate.of(a, m, j);
            System.out.println("Vous avez choisi la date : " + date.getDayOfMonth() + "-" + date.getMonth() + "-" + date.getYear());

            double coutTotalConso = 0.0; //ConsommationDAO.getConsommationsByCompteurDate(em, CompteurDAO.find(em, numC), date);
            System.out.println("A la date #" + date + " et pour le compteur #" + numC + ", le cout total des consommations est de " + coutTotalConso + "€.");
        }
        else {
            displayedView(em, numQuestion);
        }


    }



}