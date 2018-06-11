package foodbook.android.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import foodbook.android.model.City;
import foodbook.android.model.Reservation;
import foodbook.android.model.ReservationTable;
import foodbook.android.model.Restaurant;
import foodbook.android.model.RestaurantTable;
import foodbook.android.model.enumerations.RestaurantType;
import foodbook.android.repository.CityRepository;
import foodbook.android.repository.CountryRepository;
import foodbook.android.repository.ReservationRepository;
import foodbook.android.repository.ReservationTableRepository;
import foodbook.android.repository.RestaurantRepository;
import foodbook.android.repository.RestaurantTableRepository;
import foodbook.android.repository.UserRepository;
import foodbook.android.rest.dto.ReservationRequestDTO;
import foodbook.android.rest.dto.ReservationResponseDTO;

@Service
@Transactional
public class RestaurantService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailService mailManager;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantTableRepository restaurantTableRepository;

	@Autowired
	private ReservationTableRepository reservationTableRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	public List<ReservationResponseDTO> getFreeRestaurantsForReservation(ReservationRequestDTO dto) {

		City city = cityRepository.findByName(dto.getCity());

		List<Restaurant> restaurants = restaurantRepository.findByCityAndType(city,
				RestaurantType.toEnum(dto.getCuisine()));
		Date beginDate = dto.getDate();
		int hours = Integer.parseInt(dto.getBegin().split(":")[0]);
		int minutes = Integer.parseInt(dto.getBegin().split(":")[1]);
		beginDate.setHours(hours);
		beginDate.setMinutes(minutes);
		Date endDate = new Date(beginDate.getTime() + dto.getDuration());

		// konacni korak --> dobavi sve slobodne stolove jednog restorana za odredjeni
		// datum i odredjeno vreme(interval)

		// 1. za dati restoran dobavi sve rezervacije za taj interval

		/*
		 * Restoran in Restorans List<Stol> sviStolovi = getSviStolovi(Restoran)
		 * List<Rezervacija> rezervacija = getSveRezervacijeUTerminu(Restoran, pocetak,
		 * kraj); List<Stol> rezervisaniStolovi = getSveRezervisaneStolove(rezervacije);
		 * ->> listStolova = [] ->> for Rezervacija in Rezervacije
		 * listaStolova.addAll(revervazija.getStolovi());
		 * 
		 * List<Stol> slobodni = sviStolovi.filter(rezervisani);
		 */
		List<ReservationResponseDTO> responseDtos = new ArrayList<>();

		for (Restaurant restaurant : restaurants) {
			getAvailableSeatsInRestaurant(dto, beginDate, endDate, responseDtos, restaurant);
		}

		return responseDtos;
	}

	private void getAvailableSeatsInRestaurant(ReservationRequestDTO dto, Date beginDate, Date endDate, List<ReservationResponseDTO> responseDtos, Restaurant restaurant) {
		List<RestaurantTable> allTables = getAllTables(restaurant);
		List<Reservation> reservations = getAllReservationsInInterval(restaurant, beginDate, endDate);
		List<RestaurantTable> reservedTables = getAllReservedTables(reservations);

		List<RestaurantTable> availableTables = filterTables(allTables, reservedTables);
		int availableSeats = getTotalSeats(availableTables);

		if (availableSeats >= dto.getSeats()) {
			ReservationResponseDTO responseDto = new ReservationResponseDTO();
			responseDto.setAbout("");
			responseDto.setImageUrl(restaurant.getImageUrl());
			responseDto.setRestaurantId(restaurant.getId());
			responseDto.setRestaurantName(restaurant.getName());
			responseDto.setRestaurantContact(restaurant.getContact());

			responseDtos.add(responseDto);
		}
	}

	private int getTotalSeats(List<RestaurantTable> availableTables) {
		int total = 0;

		for (RestaurantTable rt : availableTables) {
			total += rt.getMax_seats();
		}

		return total;
	}

	private List<RestaurantTable> filterTables(List<RestaurantTable> allTables, List<RestaurantTable> reservedTables) {
		List<RestaurantTable> availableTables = new ArrayList<>(allTables);

		for (RestaurantTable rt : allTables) {
			for (RestaurantTable rr : reservedTables) {
				if (rt.getId() == rr.getId()) {
					availableTables.remove(rt);
				}
			}
		}

		return availableTables;
	}

	private List<RestaurantTable> getAllReservedTables(List<Reservation> reservations) {

		List<RestaurantTable> tables = new ArrayList<>();

		for (Reservation r : reservations) {
			List<RestaurantTable> rt = getAllTablesFromReservation(r);
			tables.addAll(rt);
		}

		return tables;
	}

	private List<RestaurantTable> getAllTablesFromReservation(Reservation r) {
		List<RestaurantTable> tables = new ArrayList<>();
		List<ReservationTable> reserved = reservationTableRepository.findByReservation(r);
		for (ReservationTable rt : reserved) {
			tables.add(rt.getRestaurantTable());
		}
		return tables;
	}

	private List<Reservation> getAllReservationsInInterval(Restaurant restaurant, Date beginDate, Date endDate) {
		List<Reservation> beginIncluded = findAllBetweenDates(restaurant, beginDate, endDate); 

		return beginIncluded;
	}
	
	private List<Reservation> findAllBetweenDates(Restaurant restaurant, Date beginDate, Date endDate) {
		List<Reservation> allReservations = reservationRepository.findByRestaurant(restaurant); 
		
		List<Reservation> reservations = new ArrayList<>(); 
		for(Reservation r : allReservations) {
			if((r.getBegin().getTime() > beginDate.getTime() && r.getBegin().getTime() < endDate.getTime()) || (r.getEnd().getTime() < endDate.getTime() && r.getEnd().getTime() > beginDate.getTime())) {
				reservations.add(r); 
			}
		}
		
		return reservations; 
		
	}

	private List<RestaurantTable> getAllTables(Restaurant restaurant) {
		List<RestaurantTable> allTables = restaurantTableRepository.findByRestaurant(restaurant);
		return allTables;
	}

	public List<RestaurantTable> getAvailableTablesInRestaurant(ReservationRequestDTO dto, Date beginDate, Date endDate) {
		List<RestaurantTable> tables = new ArrayList<>(); 
		
		Restaurant restaurant = restaurantRepository.findOne(dto.getRestaurantId()); 
		
		List<RestaurantTable> allTables = getAllTables(restaurant);
		List<Reservation> reservations = getAllReservationsInInterval(restaurant, beginDate, endDate);
		List<RestaurantTable> reservedTables = getAllReservedTables(reservations);

		List<RestaurantTable> availableTables = filterTables(allTables, reservedTables);
		int availableSeats = getTotalSeats(availableTables);

		if (availableSeats < dto.getSeats()) {
			return tables; 
		}
		
		int reservedSeats = 0; 
		while(reservedSeats < dto.getSeats()) {
			reservedSeats += availableTables.get(0).getMax_seats(); 
			tables.add(availableTables.get(0)); 
			availableTables.remove(0); 		
		}
		return tables; 
	}

}
