package VUE;

import CONTROLEUR.Controleur;

public class Vue {
    private Controleur controleur;

    Vue(Controleur ctrlr) {
        this.controleur = ctrlr;
    }

    public void displayTarif() {
        System.out.println(controleur.getTarif().toString());
    }

    public void displayTarifCreux() {
        System.out.println(controleur.getTarifCreux().toString());
    }

    public void displayTarifPlein() {
        System.out.println(controleur.getTarifPlein().toString());
    }

    public void displayConsommation() {
        System.out.println(controleur.getConsommation().toString());
    }

    public void displayCompteur() {
        System.out.println(controleur.getCompteur().toString());
    }
}
