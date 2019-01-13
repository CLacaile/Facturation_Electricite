package CONTROLEUR;

import MODELE.*;
import VUE.Vue;

public class Controleur {
    private Compteur compteur;
    private Consommation consommation;
    private Tarif tarif;
    private TarifCreux tarifCreux;
    private TarifPlein tarifPlein;
    private Vue vue;

    public Compteur getCompteur() {
        return compteur;
    }

    public void setCompteur(Compteur compteur) {
        this.compteur = compteur;
    }

    public Consommation getConsommation() {
        return consommation;
    }

    public void setConsommation(Consommation consommation) {
        this.consommation = consommation;
    }

    public Tarif getTarif() {
        return tarif;
    }

    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
    }

    public TarifCreux getTarifCreux() {
        return tarifCreux;
    }

    public void setTarifCreux(TarifCreux tarifCreux) {
        this.tarifCreux = tarifCreux;
    }

    public TarifPlein getTarifPlein() {
        return tarifPlein;
    }

    public void setTarifPlein(TarifPlein tarifPlein) {
        this.tarifPlein = tarifPlein;
    }

    public Vue getVue() {
        return vue;
    }

    public void setVue(Vue vue) {
        this.vue = vue;
    }
}
