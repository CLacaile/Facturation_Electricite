package MODELE;

import javax.persistence.*;

/**
 * Les tarifs peuvent être différents selon la puissance souscrite par ex :
 * - tarif 1 : 5 kWh = 1€/kWh
 * - tarif 2 : 10 kWh = 1,7€/kWh
 * - tarif 3 : 20 kWh = 3€/kWh
 * - tarif 4 : 40 kWh = 5€/kWh
 */


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="typeTarif", discriminatorType = DiscriminatorType.INTEGER)
public class Tarif {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long code;
	private double prix;
	@ManyToOne(cascade = CascadeType.ALL)
	private Horaires horaires;
	
	/**
	 * @return the code
	 */
	public long getCode() {
		return code;
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

	public Horaires getHoraires() {
		return horaires;
	}

	public void setHoraires(Horaires horaires) {
		this.horaires = horaires;
	}
}
