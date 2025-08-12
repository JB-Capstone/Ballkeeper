package capstone.ballkeeper;

import capstone.ballkeeper.domain.ReservationItem;
import capstone.ballkeeper.domain.UsageStatus;
import capstone.ballkeeper.domain.item.Item;
import capstone.ballkeeper.domain.item.ItemStatus;
import capstone.ballkeeper.domain.reservation.Reservation;
import capstone.ballkeeper.domain.reservation.ReservationStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class BallkeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(BallkeeperApplication.class, args);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ballKeeper");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();

		tx.begin();

		try {

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}

}
