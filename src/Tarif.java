import javax.persistence.*;

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
	 * @param code the code to set
	 */
	public void setCode(long code) {
		this.code = code;
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

}
