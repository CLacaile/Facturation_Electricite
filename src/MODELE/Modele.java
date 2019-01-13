package MODELE;

public class Modele {
    private TarifCreux tarifCreux;
    private Compteur compteur;

    public TarifCreux getTarifCreux() {
        return tarifCreux;
    }

    public void setTarifCreux(TarifCreux tarifCreux) {
        this.tarifCreux = tarifCreux;
    }

    public Compteur getCompteur() {
        return compteur;
    }

    public void setCompteur(Compteur compteur) {
        this.compteur = compteur;
    }
}
