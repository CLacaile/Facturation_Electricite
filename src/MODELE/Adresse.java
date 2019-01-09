package MODELE;

import javax.persistence.*;

@Entity
public class Adresse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String rue;
	private String ville;
	@OneToOne(cascade = CascadeType.ALL)
	private Compteur compteur;
	@OneToOne(cascade = CascadeType.ALL)
	private Personne personne;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the rue
	 */
	public String getRue() {
		return rue;
	}
	/**
	 * @param rue the rue to set
	 */
	public void setRue(String rue) {
		this.rue = rue;
	}
	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}
	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}
	/**
	 * @return Compteur the compteur
	 */
	public Compteur getCompteur() { return compteur;}
	/**
	 * @param compteur the compteur to set
	 */
	public void setCompteur(Compteur compteur) { this.compteur = compteur;}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
}
