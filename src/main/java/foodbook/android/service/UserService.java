package foodbook.android.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foodbook.android.model.City;
import foodbook.android.model.Country;
import foodbook.android.model.Reservation;
import foodbook.android.model.ReservationTable;
import foodbook.android.model.Restaurant;
import foodbook.android.model.RestaurantTable;
import foodbook.android.model.User;
import foodbook.android.model.enumerations.RestaurantType;
import foodbook.android.repository.CityRepository;
import foodbook.android.repository.CountryRepository;
import foodbook.android.repository.ReservationRepository;
import foodbook.android.repository.ReservationTableRepository;
import foodbook.android.repository.RestaurantRepository;
import foodbook.android.repository.RestaurantTableRepository;
import foodbook.android.repository.UserRepository;
import foodbook.android.rest.ChangePasswordDTO;
import foodbook.android.rest.dto.LoginDTO;
import foodbook.android.rest.dto.ReservationRequestDTO;
import foodbook.android.rest.dto.ReservationResponseDTO;
import foodbook.android.rest.dto.UserDTO;


@Service
@Transactional
public class UserService {

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
	
	/**
	 * Request BCrypt2 encoder
	 * 
	 * @return
	 */
	//@Autowired
	//private PasswordEncoder passwordEncoder;

	public User registerUser(User user) {
		
		City city = cityRepository.findByName("Novi Sad"); 
		Country country = countryRepository.findByName("Republika Srbija"); 
		User newUser = new User(); 
		newUser.setPassword(user.getPassword());
		newUser.setAddress(user.getAddress());
		newUser.setCity(city);
		newUser.setConfirmed_mail(false);
		newUser.setContact("");
		newUser.setEmail(user.getEmail());
		newUser.setName(user.getName());
		newUser.setSurname(user.getSurname());
		newUser.setUsername(user.getUsername());
		
		User saved = userRepository.save(newUser); 
		
		System.out.println(saved);

		if (saved != null) {
			System.out.println("MAIL SENT TO " + newUser.getEmail());
			mailManager.sendMail(newUser);

			return saved;
		}
		return null;
	}
	
	
	public boolean verifyUser(long id) {
		User user = userRepository.findOne(id);
		System.out.println("********************************");
		System.out.println(id);
		System.out.println("********************************");
		
		if (user == null) {
			return false;
		}
		user.setConfirmed_mail(true);
		userRepository.save(user);
		return true;
	}
	/*
	public ProfilePageDTO getProfilePageInfo(Long id) {
		ProfilePageDTO dto = new ProfilePageDTO();

		Guest guest = guestRepository.findOne(id);
		if (guest == null) {
			return null;
		}
		dto.setName(guest.getName());
		dto.setSurname(guest.getSurname());
		dto.setAddress(guest.getAddress());
		long numberOfVisits = 0;
		numberOfVisits += reservationRepository.countByGuest(guest);
		numberOfVisits += invitedToReservationRepository.countByGuest(guest);
		dto.setNumberOfVisits(numberOfVisits);
		List<Guest> friends = guest.getFriends();
		List<ProfilePageDTO> friendsDTO = new ArrayList<>();
		for (Guest friend : friends) {
			ProfilePageDTO friendDTO = new ProfilePageDTO();
			friendDTO.setId(friend.getId());
			friendDTO.setName(friend.getName());
			friendDTO.setSurname(friend.getSurname());
			friendsDTO.add(friendDTO);

		}
		dto.setFriends(friendsDTO);
		return dto;
	}*/


	public UserDTO loginUser(LoginDTO dto) {
		User user = userRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword()); 
		if(!user.isConfirmed_mail()) {
			return null; 
		}
		UserDTO userDto = null; 
		if(user != null)
			userDto = new UserDTO(user.getName(), user.getSurname(), user.getId());
		return userDto; 
	}


	public LoginDTO changePassword(ChangePasswordDTO dto) {
		
		User user = userRepository.findOne(dto.getUserId()); 
		LoginDTO loginDto = null; 
		
		if(user != null) {
			user.setPassword(dto.getNewPassword());
			user = userRepository.save(user); 
			loginDto = new LoginDTO(user.getUsername(), user.getPassword()); 
			
			return loginDto; 
		}
		
		return null;
	}


		
}