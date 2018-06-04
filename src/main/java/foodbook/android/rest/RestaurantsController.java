package foodbook.android.rest;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import foodbook.android.rest.dto.ReservationRequestDTO;
import foodbook.android.rest.dto.ReservationResponseDTO;
import foodbook.android.service.RestaurantService;


@RequestMapping("/api/restaurant")
@RestController
public class RestaurantsController {

	@Autowired
	private RestaurantService restaurantService;
	
	
	@RequestMapping(value = "/available-restaurants-for-reservation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> loginUser(@RequestBody ReservationRequestDTO dto) {
		List<ReservationResponseDTO> responseDto = restaurantService.getFreeRestaurantsForReservation(dto);

		if (responseDto != null) {
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(responseDto, HttpStatus.BAD_REQUEST);
	}
}
