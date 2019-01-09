import javax.persistence.Entity;

@Entity
public class Creux extends Tarif {
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
