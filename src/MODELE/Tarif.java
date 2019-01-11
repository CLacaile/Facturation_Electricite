package MODELE;

import javax.persistence.*;

/**
 * Les tarifs peuvent être différents selon la puissance souscrite par ex :
 * - tarif 1 (plein): 5 kWh = 1€/kWh | tarif 1 (creux) : 5kWh = 0.8€/kWh
 * - tarif 2 (plein) : 10 kWh = 1,7€/kWh | tarif 2 (creux) : 10 kWh = 1.5€/kWh
 * - tarif 5 (plein) : 20 kWh = 3€/kWh | tarif 6 (creux) : 20 kWh = 2.5€/kWh
 * - tarif 4 (plein) : 40 kWh = 5€/kWh
 */


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="typeTarif", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Tarif {
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
