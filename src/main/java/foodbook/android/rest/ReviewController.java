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

import foodbook.android.rest.dto.ReviewDTO;
import foodbook.android.service.ReviewService;

@RequestMapping("/api/reviews")
@RestController
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@GetMapping(value = "/{restaurantId}")
	public ResponseEntity<?> getAllReviewsByRestaurantId(@PathVariable(value = "restaurantId") Long restaurantId) {
		List<ReviewDTO> reviews = reviewService.getAllReviewsByRestaurantId(restaurantId);

		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}

	@PostMapping(value = "/comment")
	public ResponseEntity<?> addComment(@RequestBody ReviewDTO review) {
		ReviewDTO rdto = reviewService.addComment(review);

		return new ResponseEntity<>(rdto, HttpStatus.OK);
	}
}
