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

        LocalDate date1 = LocalDate.of(2019, 1, 9);
        LocalDate date2 = LocalDate.of(2019, 1, 10);
        LocalTime time1 = LocalTime.of(18,0);
        LocalTime time2 = LocalTime.of(22,0);
        LocalTime debutCreux = LocalTime.of(19,0);
        LocalTime finCreux = LocalTime.of(15,0);
        LocalTime time4 = LocalTime.of(23, 59);

        try {
            // Test of Personne, Adresse and Compteur + DAO
            Personne p1 = PersonneDAO.createPersonne(em, 11111, "0606060606");
            Personne p2 = PersonneDAO.createPersonne(em, 22222, "0707070707");
            Personne p3 = PersonneDAO.createPersonne(em, 33333, "0808080808");

            Adresse a1 = AdresseDAO.createAdresse(em, "Parmentier", "Tours");
            Adresse a2 = AdresseDAO.createAdresse(em, "Eupatoria", "Tours");
            Adresse a3 = AdresseDAO.createAdresse(em, "Bordeaux", "Tours");

            p1 = PersonneDAO.updateAdresse(em, p1, a1);
            p2 = PersonneDAO.updateAdresse(em, p2, a2);
            p3 = PersonneDAO.updateAdresse(em, p3, a3);

            Compteur compteur1 = CompteurDAO.createCompteur(em, date1, a1);
            Compteur compteur2 = CompteurDAO.createCompteur(em, date1, a2);
            Compteur compteur3 = CompteurDAO.createCompteur(em, date1, a3);

            CompteurDAO.updatePersonne(em, compteur1, p1);
            CompteurDAO.updatePersonne(em, compteur2, p2);
            CompteurDAO.updatePersonne(em, compteur3, p3);

            Consommation cons1 = ConsommationDAO.createConsommation(em, date1, time1, time2, 100, compteur1);
            Consommation cons2 = ConsommationDAO.createConsommation(em, date1, time2, time4, 100, compteur1);
            Consommation cons3 = ConsommationDAO.createConsommation(em, date1, time1, time4, 100, compteur1);

            compteur1 = CompteurDAO.addConsommation(em, compteur1, cons1);
            compteur1 = CompteurDAO.addConsommation(em, compteur1, cons2);
            compteur2 = CompteurDAO.addConsommation(em, compteur2, cons2);
            compteur3 = CompteurDAO.addConsommation(em, compteur3, cons3);

            Tarif tarif1 = TarifDAO.createTarif(em, 1, 0.1, debutCreux, finCreux);
            Tarif tarif2 = TarifDAO.createTarif(em, 2, 0.2, debutCreux, finCreux);
            Tarif tarif3 = TarifDAO.createTarif(em, 3, 0.3, debutCreux, finCreux);

            cons1 = ConsommationDAO.addTarif(em, cons1, tarif1);
            cons1 = ConsommationDAO.addTarif(em, cons1, tarif2);
            cons2 = ConsommationDAO.addTarif(em, cons2, tarif2);
            cons3 = ConsommationDAO.addTarif(em, cons3, tarif3);
/*
            // Question 1 : Les conso pour le tarif creux #10 et #13?
            ///a) getConsomationsByTarif
            Tarif tarif10 = TarifDAO.getTarifByTarifCreux(em, TarifCreuxDAO.find(em, 10));
            List<Consommation> consoTarif10 = ConsommationDAO.getConsommationsByTarif(em, tarif10);
            for(Consommation c : consoTarif10) {
                System.out.println("A la conso #"+c.getId()+" a été appliquée le tarif creux #10");
            }
            ///b) getConsommationsByTarifCreux
            List<Consommation> consoTarif13 = ConsommationDAO.getConsommationsByTarifCreux(em, TarifCreuxDAO.find(em, 13));
            for(Consommation c : consoTarif13) {
                System.out.println("A la conso #"+c.getId()+" a été appliquée le tarif creux #13");
            }
*/
            /**
             * Call the function displayedView to answer the first question
             */
            //displayedView(em, 1);

            /**
             * Call the function displayedView to answer the second question
             */
            //displayedView(em, 2);

            // Question 2 : Le cout des conso d'un compteur à une date donnée
            System.out.println("Cout de cons1 : "+ ConsommationDAO.computeCost(em, cons1));
            System.out.println("Cout total du compteur 1 au 09-01: "+CompteurDAO.computeCost(em, compteur1, date1));

            Modele modele = new Modele();
            Vue vue = new Vue();
            Controleur controleur = new Controleur(em, modele, vue);
            controleur.execute();



        } catch (Exception e){
            e.printStackTrace();
        }
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