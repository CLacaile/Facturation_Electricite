import java.time.LocalDate;

public class Compteur {
	private long numC;
	private Adresse adresse;
	private LocalDate date;
	
	/**
	 * @return the numC
	 */
	public long getNumC() {
		return numC;
	}
	/**
	 * @param numC the numC to set
	 */
	public void setNumC(long numC) {
		this.numC = numC;
	}
	/**
	 * @return the adresse
	 */
	public Adresse getAdresse() {
		return adresse;
	}
	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
}
