package foodbook.android.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbook.android.rest.dto.RatingDTO;
import foodbook.android.rest.dto.ReviewDTO;
import foodbook.android.service.RatingService;
import foodbook.android.service.ReviewService;

@RequestMapping("/api/ratings")
@RestController
public class RatingController {

	@Autowired
	private RatingService ratingService;
//
//	@GetMapping(value = "/{restaurantId}")
//	public ResponseEntity<?> getAllReviewsByRestaurantId(@PathVariable(value = "restaurantId") Long restaurantId) {
//		List<ReviewDTO> reviews = reviewService.getAllReviewsByRestaurantId(restaurantId);
//
//		return new ResponseEntity<>(reviews, HttpStatus.OK);
//	}
//
	@PostMapping(value = "/rate")
	public ResponseEntity<?> rateRestaurant(@RequestBody RatingDTO ratingDTO) {
		Double restaurantRate = ratingService.rateRestaurant(ratingDTO);

		return new ResponseEntity<>(restaurantRate, HttpStatus.OK);
	}

	@GetMapping(value = "/{restaurantId}")
	public ResponseEntity<?> getRatingForRestaurant(@PathVariable(value = "restaurantId") Long restaurantId) {
		Double restaurantRate = ratingService.getRatingForRestaurant(restaurantId);

		return new ResponseEntity<>(restaurantRate, HttpStatus.OK);
	}


}
