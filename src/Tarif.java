public abstract class Tarif {
	private long code;
	private double prix;
	
	/**
	 * @return the code
	 */
	public long getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(long code) {
		this.code = code;
	}
	/**
	 * @return the prix
	 */
	public double getPrix() {
		return prix;
	}
	/**
	 * @param prix the prix to set
	 */
	public void setPrix(double prix) {
		this.prix = prix;
	}

}
