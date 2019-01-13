package MODELE;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class TarifPlein {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long code;
    private double prix;
    private LocalTime heureDeb;
    private LocalTime heureFin;
    @OneToOne(cascade = CascadeType.ALL)
    private Tarif tarif;

    public long getCode() {
        return code;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public LocalTime getHeureDeb() {
        return heureDeb;
    }

    public void setHeureDeb(LocalTime heureDeb) {
        this.heureDeb = heureDeb;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public Tarif getTarif() {
        return tarif;
    }

    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
    }

    public String toString() {
        return "Code : " + this.code
                + "; Prix : " + this.prix
                + "; Heure de debut : " + this.heureDeb
                + "; Heure de fin : " + this.heureFin;
    }
}
