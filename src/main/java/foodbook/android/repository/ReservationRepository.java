package foodbook.android.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import foodbook.android.model.Reservation;
import foodbook.android.model.Restaurant;
import foodbook.android.model.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findByBeginBetweenOrEndBetween(Date date1, Date date2, Date date3, Date date4);

	List<Reservation> findByRestaurantAndBeginBetween(Restaurant restaurant, Date date1, Date date2);
	List<Reservation> findByRestaurantAndEndBetween(Restaurant restaurant, Date date1, Date date2);

	List<Reservation> findByRestaurant(Restaurant restaurant);

	List<Reservation> findByOwner(User user); 
	
	 List<Reservation> findByBeginBetween(Date now, Date next30Minutes);
	
	
}