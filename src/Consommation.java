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
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	public void setHoraires(ArrayList<Horaires> horaires) {
		this.horaires = horaires;
	}

}
