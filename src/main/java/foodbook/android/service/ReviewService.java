package foodbook.android.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbook.android.model.Review;
import foodbook.android.repository.ReviewRepository;
import foodbook.android.rest.dto.ReviewDTO;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	public List<ReviewDTO> getAllReviewsByRestaurantId(Long restaurantId) {
		List<Review> reviews =  reviewRepository.findAllByRestaurant_id(restaurantId);
		return reviews.stream().map(this::transformToReviewDTO).collect(Collectors.toList());
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
