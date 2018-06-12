package foodbook.android.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbook.android.model.Restaurant;
import foodbook.android.model.Review;
import foodbook.android.model.User;
import foodbook.android.repository.RestaurantRepository;
import foodbook.android.repository.ReviewRepository;
import foodbook.android.repository.UserRepository;
import foodbook.android.rest.dto.ReviewDTO;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	public List<ReviewDTO> getAllReviewsByRestaurantId(Long restaurantId) {
		List<Review> reviews =  reviewRepository.findAllByRestaurant_id(restaurantId);
		return reviews.stream().map(this::transformToReviewDTO).collect(Collectors.toList());
	}

	public ReviewDTO addComment(final ReviewDTO reviewDTO) {
		Review review = new Review();
		User u = userRepository.findByName(reviewDTO.getUser());
		review.setUser(u);
		Restaurant r = restaurantRepository.findOne(reviewDTO.getRestaurantId());
		review.setRestaurant(r);
		review.setDate(LocalDateTime.now());
		review.setComment(reviewDTO.getText());

		return transformToReviewDTO(reviewRepository.save(review));
	}

	private ReviewDTO transformToReviewDTO(final Review rew) {
		ReviewDTO rdto = new ReviewDTO();
		rdto.setDate(rew.getDate());
		rdto.setRate(rew.getRatting());
		rdto.setRestaurantId(rew.getRestaurant().getId());
		rdto.setText(rew.getComment());
		rdto.setUser(rew.getUser().getUsername());
		return rdto;
	}
}
