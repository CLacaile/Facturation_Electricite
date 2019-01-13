package MODELE;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Compteur {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long numC;
	private LocalDate date;
	@OneToOne(mappedBy = "compteur", cascade = CascadeType.ALL)
	private Adresse adresse;
	@OneToMany(mappedBy = "compteur", cascade = CascadeType.ALL)
	private List<Consommation> consommations = new ArrayList<>();
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

    public List<Consommation> getConsommations() {
        return consommations;
    }

    public void setConsommations(List<Consommation> consommations) {
        this.consommations = consommations;
    }

	/**
	 * Return a string representation of the compteur
	 * @return a string
	 */
    public String toString() {
		return "Numero de compteur : " + numC
				+ "; Date d'activation : " + date
				+ "; Adresse : " + adresse.toString()
				+ "; Personne liée au compteur : " + personne.toString();
	}
}
