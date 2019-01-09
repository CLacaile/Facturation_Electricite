package MODELE;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Consommation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne(cascade = CascadeType.ALL)
    private Compteur compteur;
	@OneToMany(mappedBy = "consommation", cascade = CascadeType.ALL)
	private List<Horaires> horaires;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the horaires
	 */
	public List<Horaires> getHoraires() {
		return horaires;
	}

	/**
	 * @param horaires the horaires to set
	 */
	public void setHoraires(List<Horaires> horaires) {
		this.horaires = horaires;
	}

	public Compteur getCompteur() {
		return compteur;
	}

	public void setCompteur(Compteur compteur) {
		this.compteur = compteur;
	}
}
