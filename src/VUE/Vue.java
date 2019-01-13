package VUE;

import MODELE.Modele;

import java.time.LocalDate;
import java.util.Scanner;

public class Vue {
    private Modele modele;

    public Vue(Modele m) {
        this.modele = m;
        System.out.println("========= Facturation Electricite =========");
        int choix = displayMenu();
    }

    public int displayMenu() {
        System.out.println("Entrez votre choix: ");
        System.out.println("1. Verifier a quelle consommation a ete applique un tarif creux");
        System.out.println("2. Calculer le cout des consommations d'un compteur a une date");
        int choix = scanInteger();
        while (choix > 2 && choix < 1) {
            choix = displayMenu();
        }
        return choix;
    }

    public void display(String s) {
        System.out.println(s);
    }

    public int scanInteger() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int res = 0;
        try {
            res = Integer.valueOf(str);
        } catch(NumberFormatException e) {
            System.out.println("Erreur : veuillez entrer un nombre entier");
        }
        sc.close();
        return res;
    }

    public double scanDouble() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        double res = Double.parseDouble(str);
        sc.close();
        return res;
    }

    public LocalDate scanDate() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        LocalDate date = LocalDate.parse(str);
        sc.close();
        return date;
    }
}
