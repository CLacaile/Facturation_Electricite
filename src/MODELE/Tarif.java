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
public class Tarif {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private double reduction;
	@OneToOne(mappedBy = "tarif", cascade = CascadeType.ALL)
	private TarifCreux tarifCreux;
	@OneToOne(mappedBy = "tarif", cascade = CascadeType.ALL)
	private TarifPlein tarifPlein;
	@ManyToOne(cascade = CascadeType.ALL)
	private Horaires horaires;

	public long getId() {
		return id;
	}

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    public TarifCreux getTarifCreux() {
		return tarifCreux;
	}

	public void setTarifCreux(TarifCreux tarifCreux) {
		this.tarifCreux = tarifCreux;
	}

	public TarifPlein getTarifPlein() {
		return tarifPlein;
	}

	public void setTarifPlein(TarifPlein tarifPlein) {
		this.tarifPlein = tarifPlein;
	}

	public Horaires getHoraires() {
		return horaires;
	}

	public void setHoraires(Horaires horaires) {
		this.horaires = horaires;
	}
}
