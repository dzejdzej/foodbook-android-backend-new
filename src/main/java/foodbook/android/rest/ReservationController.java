package foodbook.android.rest;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import foodbook.android.rest.dto.CreatedReservationDTO;
import foodbook.android.rest.dto.InviteFriendsDTO;
import foodbook.android.rest.dto.ReservationDTO;
import foodbook.android.rest.dto.ReservationRequestDTO;
import foodbook.android.rest.dto.ReservationResponseDTO;
import foodbook.android.service.ReservationService;

@RequestMapping("/api/reservation")
@RestController
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	
	@RequestMapping(value = "/make-reservation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> makeReservation(@RequestBody ReservationRequestDTO dto) {
		CreatedReservationDTO responseDto = reservationService.makeReservation(dto);

		if (responseDto != null) {
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(responseDto, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/finish-reservation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> finishReservation(@RequestBody InviteFriendsDTO dto) {
		InviteFriendsDTO responseDto = reservationService.finishReservation(dto);

		if (responseDto != null) {
			return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(responseDto, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/cancel-reservation/user/{userId}/reservation/{reservationId}")
	public ResponseEntity<?> cancelReservation(@PathVariable(value = "userId") Long userId, @PathVariable(value = "reservationId") Long reservationId) {
		ReservationDTO reservation = reservationService.cancelReservation(userId, reservationId);

		List<ReservationDTO> reservations = reservationService.getAllReservationsForUser(userId); 
		
		return new ResponseEntity<>(reservations, HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/get-all-reservations/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllReservationsForUser(@PathVariable(value = "userId") long id) {

		List<ReservationDTO> reservations = reservationService.getAllReservationsForUser(id);

		if (reservations != null) {
			return new ResponseEntity<Object>(reservations, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(reservations, HttpStatus.BAD_REQUEST);
	}
	
	
}