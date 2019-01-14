package MODELE;

import MODELE.Adresse;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Personne {
	@Id
	private long numSS;
	private String numTel;
	@OneToOne(mappedBy = "personne", cascade = CascadeType.ALL)
	private Adresse adresse;
	@OneToOne(mappedBy = "personne", cascade = CascadeType.ALL)
	private Compteur compteur;

	/**
	 * @return the numSS
	 */
	public long getNumSS() {
		return numSS;
	}
	/**
	 * @param numSS the numSS to set
	 */
	public void setNumSS(long numSS) {
		this.numSS = numSS;
	}
	/**
	 * @return the numTel
	 */
	public String getNumTel() {
		return numTel;
	}
	/**
	 * @param numTel the numTel to set
	 */
	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public Compteur getCompteur() {return compteur;}

	public void setCompteur(Compteur compteur) {this.compteur = compteur;}

	public Adresse getAdresse() {return adresse;}

	public void setAdresse(Adresse adresse) {this.adresse = adresse;}

	/**
	 * Retourne la personne sous la forme d'une chaîne de caractères
	 * @return Numero de securite sociale : #numSS; Numero de telephone : #numTel
	 */
	public String toString() {
		return "Numero de securite sociale : " + numSS
				+ "; Numero de telephone : " + numTel;
	}

}
