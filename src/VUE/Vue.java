package VUE;

import MODELE.Consommation;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Vue {

    public Vue() {
        System.out.println("========= Facturation Electricite =========");
        displayMenu();
    }

    public void displayMenu() {
        System.out.println("Entrez votre choix: ");
        System.out.println("1. Verifier a quelle consommation a ete applique un tarif creux");
        System.out.println("2. Calculer le cout des consommations d'un compteur a une date");
    }

    public void display(String s) {
        System.out.println(s);
    }

    public void displayCost(double cost) {
        System.out.println("Le cout est de: "+cost);
    }

    public void displayConsommation(Consommation conso) {
        System.out.println(conso.toString());
    }

    public void displayConsommations(List<Consommation> consommations) {
        if(consommations.size() == 0) {
            display("Pas de consommations à afficher");
        }
        else {
            for(Consommation c : consommations) {
                displayConsommation(c);
            }
        }
    }

    public int scanInteger() {
        String str = scanCommand();
        int res = 0;
        try {
            res = Integer.parseInt(str);
        } catch(NumberFormatException e) {
            System.out.println("Erreur : veuillez entrer un nombre entier");
        }
        return res;
    }

    public double scanDouble() {
        System.out.print(">> ");
        Scanner sc = new Scanner(System.in);
        String str = scanCommand();
        double res = Double.parseDouble(str);
        return res;
    }

    public LocalDate scanDate() {
        System.out.print(">> ");
        Scanner sc = new Scanner(System.in);
        String str = scanCommand();
        LocalDate date = LocalDate.parse(str);
        return date;
    }

    public String scanCommand() {
        System.out.print(">> ");
        Scanner sc = new Scanner(System.in);
        String str = "";
        while(sc.hasNextLine()) {
            str = sc.nextLine();
            return str;
        }
        return null;
    }

}
