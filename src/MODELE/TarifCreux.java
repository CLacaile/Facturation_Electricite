package MODELE;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class TarifCreux extends Tarif {
	private double reduction;
	
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

}
