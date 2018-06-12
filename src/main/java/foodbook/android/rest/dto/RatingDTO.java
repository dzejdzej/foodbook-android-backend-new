package foodbook.android.rest.dto;


public class RatingDTO {

	Long restaurantId;
	String user;
	Double rating;

	public RatingDTO() {
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(final Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(final Double rating) {
		this.rating = rating;
	}
}

