package MODELE;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalTime;

@Entity
@DiscriminatorValue("2")
public class TarifCreux extends Tarif {
	private double reduction;
	private LocalTime heureDeb;
	private LocalTime heureFin;

	/**
	 * @return the reduction
	 */
	public double getReduction() {
		return reduction;
	}
	/**
	 * @param reduction the reduction to set
	 */
	public void setReduction(double reduction) {
		this.reduction = reduction;
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
}
