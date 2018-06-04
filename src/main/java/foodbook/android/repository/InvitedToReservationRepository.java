package foodbook.android.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import foodbook.android.model.InvitedToReservation;
import foodbook.android.model.Reservation;
import foodbook.android.model.User;

@Repository
public interface InvitedToReservationRepository extends JpaRepository<InvitedToReservation, Long> {

	List<InvitedToReservation> findByUser(User user);

	List<InvitedToReservation> findByReservationAndUser(Reservation findOne, User findOne2);

	List<InvitedToReservation> findByReservation(Reservation reservation);

	
}