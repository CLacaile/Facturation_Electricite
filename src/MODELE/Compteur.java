package MODELE;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Compteur {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long numC;
	private LocalDate date;
	@OneToOne(mappedBy = "compteur", cascade = CascadeType.ALL)
	private Adresse adresse;
	@OneToOne(mappedBy = "compteur", cascade = CascadeType.ALL)
	private Consommation consommation;

	
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
