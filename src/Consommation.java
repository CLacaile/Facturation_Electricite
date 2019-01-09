import java.util.ArrayList;

public class Consommation {
	private long id;
	private ArrayList<Horaires> horaires;

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
	public ArrayList<Horaires> getHoraires() {
		return horaires;
	}

	/**
	 * @param horaires the horaires to set
	 */
	public void setHoraires(ArrayList<Horaires> horaires) {
		this.horaires = horaires;
	}

}
