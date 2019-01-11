package MODELE;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
	private double prix;
	private double reduction;
	@OneToOne(mappedBy = "tarif", cascade = CascadeType.ALL)
	private TarifCreux tarifCreux;
	@OneToOne(mappedBy = "tarif", cascade = CascadeType.ALL)
	private TarifPlein tarifPlein;
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Horaires> horaires = new ArrayList<>();

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

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

	public List<Horaires> getHoraires() {
		return horaires;
	}

	public void setHoraires(List<Horaires> horaires) {
		this.horaires = horaires;
	}


}
