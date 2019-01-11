package MODELE;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalTime;

@Entity
@DiscriminatorValue("1")
public class TarifPlein extends Tarif {
    LocalTime heureDeb;
    LocalTime heureFin;

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
}
