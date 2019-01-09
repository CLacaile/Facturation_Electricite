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
	@OneToOne(cascade = CascadeType.ALL)
	private Personne personne;
	
	/**
	 * @return the numC
	 */
	public long getNumC() {
		return numC;
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

	public Personne getPersonne() { return personne;}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
}
