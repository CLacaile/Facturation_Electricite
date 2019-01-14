package VUE;

import MODELE.Consommation;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Vue {

    public Vue() {

        //displayMenu();
    }

    /**
     * Affiche un menu dans la console de la maniere suivante :
     *  Entrez votre choix
     *  1. Verifier a quelle consommation a ete applique un tarif creux
     *  2. Calculer le cout des consommations d'un compteur a une date
     */
    public void displayMenu() {
        System.out.println("========= Facturation Electricite =========");
        System.out.println("Entrez votre choix: ");
        System.out.println("1. Verifier a quelle consommation a ete applique un tarif creux");
        System.out.println("2. Calculer le cout des consommations d'un compteur a une date");
    }

    /**
     * Affiche une chaine de caracteres dans la console.
     * @param s the string to display in the console
     */
    public void display(String s) {
        System.out.println(s);
    }

    /**
     * Affiche un cout dans la console de la maniere suivante :
     *  Le cout est de: xxx
     * @param cost the cost to display in the console
     */
    public void displayCost(double cost) {
        System.out.println("Le cout est de: "+cost);
    }

    /**
     * Scanne la console pour un entier entre par l'utilisateur depuis la console en utilisant scanCommand().
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
     * Scanne la console pour un double entre par l'utilisateur depuis la console en utilisant scanCommand().
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
     * Scanne la console pour une date de type YYYY-MM-DD entree par l'utilisateur depuis la console en utilisant scanCommand().
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
     * Scanne la console pour une entree de l'utilisateur depuis la console.
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
