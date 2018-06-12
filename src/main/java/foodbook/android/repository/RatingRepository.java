package foodbook.android.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import foodbook.android.model.Rating;
import foodbook.android.model.Restaurant;
import foodbook.android.model.Review;
import foodbook.android.model.User;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

	List<Rating> findByUserAndRestaurant(User user, Restaurant restaurant);

	List<Rating> findByRestaurant(Restaurant restaurant);

}