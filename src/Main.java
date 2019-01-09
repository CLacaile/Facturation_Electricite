import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		// Etant donné un tarif HC, on veut vérifier s'il a été appliqué à au moins une plage de consommation
		// Etant donné un compteur et une date, on veut calculer le cout total des consommations effectuées
		// le jour donné (avec application éventuelle des tarifs HC)
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bdd_hibernate");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		em.getTransaction().commit();

	}

}
