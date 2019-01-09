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
	

}
