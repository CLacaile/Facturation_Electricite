import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Horaires {
	@Id
	@GeneratedValue
	private long id;
	private LocalDate date;
	private LocalTime heureDeb;
	private LocalTime heureArr;
	private int puissance;
	@ManyToOne
	private Consommation consommation;
	@OneToMany(mappedBy = "horaires", cascade = CascadeType.ALL)
	private List<Tarif> tarifs;
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
	/**
	 * @return the heureDeb
	 */
	public LocalTime getHeureDeb() {
		return heureDeb;
	}
	/**
	 * @param heureDeb the heureDeb to set
	 */
	public void setHeureDeb(LocalTime heureDeb) {
		this.heureDeb = heureDeb;
	}
	/**
	 * @return the heureArr
	 */
	public LocalTime getHeureArr() {
		return heureArr;
	}
	/**
	 * @param heureArr the heureArr to set
	 */
	public void setHeureArr(LocalTime heureArr) {
		this.heureArr = heureArr;
	}
	/**
	 * @return the puissance
	 */
	public int getPuissance() {
		return puissance;
	}
	/**
	 * @param puissance the puissance to set
	 */
	public void setPuissance(int puissance) {
		this.puissance = puissance;
	}
	/**
	 * @return the tarifs
	 */
	public List<Tarif> getTarifs() {
		return tarifs;
	}
	/**
	 * @param tarifs the tarifs to set
	 */
	public void setTarifs(List<Tarif> tarifs) {
		this.tarifs = tarifs;
	}

}
