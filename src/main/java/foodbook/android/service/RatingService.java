package foodbook.android.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import foodbook.android.model.Rating;
import foodbook.android.model.Restaurant;
import foodbook.android.model.Review;
import foodbook.android.model.User;
import foodbook.android.repository.RatingRepository;
import foodbook.android.repository.RestaurantRepository;
import foodbook.android.repository.ReviewRepository;
import foodbook.android.repository.UserRepository;
import foodbook.android.rest.dto.RatingDTO;
import foodbook.android.rest.dto.ReviewDTO;

@Service
public class RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;


	public Double rateRestaurant(final RatingDTO ratingDTO) {

		final User u = userRepository.findByName(ratingDTO.getUser());
		final Restaurant r = restaurantRepository.findOne(ratingDTO.getRestaurantId());

		List<Rating> ratings = ratingRepository.findByUserAndRestaurant(u, r);

		Double restaurantRating;
		Rating rating;

		if (CollectionUtils.isEmpty(ratings)) {
			rating = new Rating();
			rating.setUser(u);
			rating.setRestaurant(r);
			rating.setRating(ratingDTO.getRating());

		} else {
			rating = ratings.get(0);
			rating.setRating(ratingDTO.getRating());
		}

		ratingRepository.save(rating);
		restaurantRating = calculateRestaurantRating(r);
		r.setRating(restaurantRating);
		restaurantRepository.save(r);

		return restaurantRating;

	}


	private Double calculateRestaurantRating(Restaurant restaurant) {

		List<Rating> ratings = ratingRepository.findByRestaurant(restaurant);

		Double retVal = 0d;

		if (!CollectionUtils.isEmpty(ratings)) {
			for (Rating rating : ratings) {
				retVal += rating.getRating();
			}
			retVal = retVal / ratings.size();
		}

		return Double.parseDouble(new DecimalFormat("##.##").format(retVal));
	}

	public Double getRatingForRestaurant(Long restaurantId) {
		final Restaurant restaurant = restaurantRepository.findOne(restaurantId);
		return calculateRestaurantRating(restaurant);
	}


}
