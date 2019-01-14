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

    /**
     * Display a menu in the console like so :
     * > Entrez votre choix
     * > 1. Verifier a quelle consommation a ete applique un tarif creux
     * > 2. Calculer le cout des consommations d'un compteur a une date
     */
    public void displayMenu() {
        System.out.println("Entrez votre choix: ");
        System.out.println("1. Verifier a quelle consommation a ete applique un tarif creux");
        System.out.println("2. Calculer le cout des consommations d'un compteur a une date");
    }

    /**
     * Display a string in the console
     * @param s the string to display in the console
     */
    public void display(String s) {
        System.out.println(s);
    }

    /**
     * Display a cost in the console like :
     * > Le cout est de: xxx
     * @param cost the cost to display in the console
     */
    public void displayCost(double cost) {
        System.out.println("Le cout est de: "+cost);
    }

    /**
     * Scan the console for a integer user input from the console using scanCommand()
     * @return the integer typed in by the user in the console
     */
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

    /**
     * Scan the console for a double user input from the console using scanCommand()
     * @return the double typed in by the user in the console
     */
    public double scanDouble() {
        System.out.print(">> ");
        Scanner sc = new Scanner(System.in);
        String str = scanCommand();
        double res = Double.parseDouble(str);
        return res;
    }

    /**
     * Scan the console for a date YYYY-MM-DD user input from the console using scanCommand()
     * @return the LocalDate parsed from the console
     */
    public LocalDate scanDate() {
        System.out.print(">> ");
        Scanner sc = new Scanner(System.in);
        String str = scanCommand();
        LocalDate date = LocalDate.parse(str);
        return date;
    }

    /**
     * Scan the console for a user input from the console
     * @return the string input by the user
     */
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
