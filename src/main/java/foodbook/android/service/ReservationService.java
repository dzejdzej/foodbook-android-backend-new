package foodbook.android.service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foodbook.android.model.InvitedToReservation;
import foodbook.android.model.Reservation;
import foodbook.android.model.ReservationTable;
import foodbook.android.model.Restaurant;
import foodbook.android.model.RestaurantTable;
import foodbook.android.model.User;
import foodbook.android.repository.InvitedToReservationRepository;
import foodbook.android.repository.ReservationRepository;
import foodbook.android.repository.ReservationTableRepository;
import foodbook.android.repository.RestaurantRepository;
import foodbook.android.repository.UserRepository;
import foodbook.android.rest.dto.CreatedReservationDTO;
import foodbook.android.rest.dto.InviteFriendsDTO;
import foodbook.android.rest.dto.ReservationDTO;
import foodbook.android.rest.dto.ReservationRequestDTO;


@Service
@Transactional
public class ReservationService {
	
	@Autowired
	private RestaurantRepository restaurantRepository; 
	
	@Autowired
	private RestaurantService restaurantService; 
	
	@Autowired
	private ReservationRepository reservationRepository; 
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private ReservationTableRepository reservationTableRepository; 

	@Autowired
	private InvitedToReservationRepository invitedToReservationRepository; 
	
	public CreatedReservationDTO makeReservation(ReservationRequestDTO dto) {
		
		CreatedReservationDTO created = new CreatedReservationDTO(); 
		
		Reservation reservation = new Reservation(); 
		
		Restaurant restaurant = restaurantRepository.findOne(dto.getRestaurantId());
		
		Date beginDate = dto.getDate(); 
		int hours = Integer.parseInt(dto.getBegin().split(":")[0]); 
		int minutes = Integer.parseInt(dto.getBegin().split(":")[1]); 
		beginDate.setHours(hours);
		beginDate.setMinutes(minutes);
		Date endDate = new Date(beginDate.getTime()+dto.getDuration()); 
		
		reservation.setBegin(beginDate);
		reservation.setCanceled(false);
		reservation.setCreated_date(new Date());
		reservation.setEnd(endDate);
		reservation.setLast_updated(new Date());
		reservation.setRestaurant(restaurant);
		
		User user = userRepository.findOne(dto.getUserId()); 
		reservation.setOwner(user);
		
		
		List<RestaurantTable> tables = restaurantService.getAvailableTablesInRestaurant(dto, beginDate, endDate); 
		
		if(tables.size() == 0) {
			created.setReservationId(-1);
			return created; 
		}
		
		reservation = reservationRepository.save(reservation); 
		
		for(RestaurantTable table : tables) {
			ReservationTable resTable = new ReservationTable(); 
			resTable.setReservation(reservation);
			resTable.setRestaurantTable(table);
			
			reservationTableRepository.save(resTable); 
		}
		
		
		created.setReservationId(reservation.getId());
		
		return created; 
	}

	public List<ReservationDTO> getAllReservationsForUser(long id) {
		User user = userRepository.findOne(id); 
		List<Reservation> reservations = reservationRepository.findByOwner(user); 
		List<InvitedToReservation> invitedReservations = invitedToReservationRepository.findByUser(user); 
		for(InvitedToReservation i : invitedReservations) {
			reservations.add(i.getReservation()); 
		}
		
		List<ReservationDTO> reservationsDTO = new ArrayList<>(); 
		
		for(Reservation r : reservations) {
			ReservationDTO dto = new ReservationDTO(); 
			dto.setId(r.getId());
			if(r.getRestaurant() != null && r.getRestaurant().getName() != null) {
				dto.setRestaurantName(r.getRestaurant().getName());
				dto.setImageUrl(r.getRestaurant().getImageUrl());
			}
			
			
			dto.setOwner(user.getName());
			dto.setEndDate(r.getEnd());
			dto.setBeginDate(r.getBegin());
			reservationsDTO.add(dto); 
		}
		
		reservationsDTO.sort(new CustomComparator());
		
		return reservationsDTO;
		
	}
	
	
	
	
	
	
	
	///////////////////////////////////////////
	
	
	public void cancelAttendance(Long userId, Long reservationId) {
		//ReservationDetailsDTO response = new ReservationDetailsDTO();
		List<InvitedToReservation> invitations = invitedToReservationRepository.findByReservationAndUser(reservationRepository.findOne(reservationId), userRepository.findOne(userId));
		if(invitations == null || invitations.size() != 1) {
			//logger.error("failed to fetch invitation for userId: reservationId:" + userId + ", " + reservationId);
			return;
		}
		InvitedToReservation invitation = invitations.get(0);
		invitation.setConfirmed(false);
		invitedToReservationRepository.save(invitation);
		
	}

	public void confirmAttendance(Long userId, Long reservationId) {
		//ReservationDetailsDTO response = new ReservationDetailsDTO();
		List<InvitedToReservation> invitations = invitedToReservationRepository.findByReservationAndUser(reservationRepository.findOne(reservationId), userRepository.findOne(userId));
		if(invitations == null || invitations.size() != 1) {
			//logger.error("failed to fetch invitation for guestId: reservationId:" + guestId + ", " + reservationId);
			return;
		}
		InvitedToReservation invitation = invitations.get(0);
		invitation.setConfirmed(true);
		invitedToReservationRepository.save(invitation);
		
	}

	public ReservationDTO cancelReservation(Long userId, Long reservationId) {
			ReservationDTO responseDTO = new ReservationDTO();
			Reservation reservation = reservationRepository.findOne(reservationId);
			List<InvitedToReservation> invitations = invitedToReservationRepository.findByReservation(reservation);
			
			if(reservation.getOwner().getId() == userId) {
				for(InvitedToReservation invited : invitations) {
					invitedToReservationRepository.delete(invited);
				}
				
				List<ReservationTable> tables = reservationTableRepository.findByReservation(reservation); 
				
				for(ReservationTable table : tables) {
					reservationTableRepository.delete(table); 
				}
				
				responseDTO.setId(reservation.getId());
				reservationRepository.delete(reservation);
			}
			
			for(InvitedToReservation i : invitations) {
				if(i.getUser().getId() == userId) {
					invitedToReservationRepository.delete(i);
				}
			}
			
			responseDTO.setSuccess(true);
			
			return responseDTO; 
	}

	public InviteFriendsDTO finishReservation(InviteFriendsDTO dto) {
		// TODO Auto-generated method stub
		Reservation reservation = reservationRepository.findOne(dto.getReservationId()); 
		
		for(String s : dto.getInvitedFriends()) {
			User u = userRepository.findByEmail(s); 
			if(u == null) 
				return null; 
			InvitedToReservation i = new InvitedToReservation(); 
			i.setDate(reservation.getBegin());
			i.setReservation(reservation);
			i.setUser(u);
			invitedToReservationRepository.save(i); 
		}

		return dto;
	}
	
	
	public class CustomComparator implements Comparator<ReservationDTO> {
	    @Override
	    public int compare(ReservationDTO o1, ReservationDTO o2) {
	        return o1.getBeginDate().compareTo(o2.getBeginDate());
	    }
	}

/*
	public ReservationDTO cancelReservation(ReservationDTO dto) {
		Reservation reservation = reservationRepository.findOne(dto.getId()); 
		
		for(InvitedToReservation i : reservation.getInvited()) {
			//odradi brisanje entiteta
			User u = userRepository.findByEmail(s); 
			if(u == null) 
				return null; 
			InvitedToReservation i = new InvitedToReservation(); 
			i.setDate(reservation.getBegin());
			i.setReservation(reservation);
			i.setUser(u);
			invitedToReservationRepository.save(i); 
		}

		return dto;
	}
*/
	
	@Scheduled(fixedDelay = 60000)
	public void scheduleFixedDelayTask() {
	    Date date = new Date();
		Date dateNext30Minutes = new Date(System.currentTimeMillis() + 30*60*1000);
	    
		List<Reservation> upcomingRes = reservationRepository.findByBeginBetween(date, dateNext30Minutes);
		if(upcomingRes == null || upcomingRes.size() == 0) {
			return;
		}
			for(Reservation reservation : upcomingRes) {
				System.out.println("DA RESERVATION" + reservation);
				// fgireba.send
		
		}
	}

}
