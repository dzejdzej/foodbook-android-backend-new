package foodbook.android.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import foodbook.android.model.City;
import foodbook.android.model.Country;
import foodbook.android.model.Restaurant;
import foodbook.android.model.RestaurantTable;
import foodbook.android.model.Review;
import foodbook.android.model.User;
import foodbook.android.model.enumerations.RestaurantType;
import foodbook.android.repository.CityRepository;
import foodbook.android.repository.CountryRepository;
import foodbook.android.repository.RestaurantRepository;
import foodbook.android.repository.RestaurantTableRepository;
import foodbook.android.repository.ReviewRepository;
import foodbook.android.repository.UserRepository;
import foodbook.android.service.firebase.FirebaseService;
import foodbook.android.service.firebase.NotificationDTO;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	CountryRepository countryRepository; 
	
	@Autowired
	CityRepository cityRepository; 
	
	@Autowired
	UserRepository userRepository; 
	
	@Autowired
	RestaurantRepository restaurantRepository; 
	
	@Autowired
	RestaurantTableRepository restaurantTableRepository;

	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired 
	FirebaseService firebaseService;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/test-firebase")
	public void sendFirebase() {
		NotificationDTO info = new NotificationDTO();
		
		info.setTitle("Kek");
		info.setNotificationType("Invited");
		info.setInfoMessage("Some text");
		info.setInviteReservationId(1L);
			
		// OVO JE ZA CANCEL
		// for(InvitedUSerResavation user : invited) {
		/****
		 *   Notification dto = new ..
		 *   dto.setTitle
		 *   setMessage
		 * 		firebaseService.send("" + user.getId(), dto)
		 * 
		 * ZA INVITE
		 * setReservationId
		 */
		
		
		
		
		
		firebaseService.sendMessage("1", info);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/fill-database")
	public ResponseEntity<?> getProfilePageInfo() {
		
		Country country1 = new Country(); 
		country1.setCode("RS");
		country1.setName("Republika Srbija");
		country1 = countryRepository.save(country1); 
		
		City city1 = new City(); 
		city1.setCountry(country1);
		city1.setName("Novi Sad");
		city1.setPostal_code("21000");
		city1 = cityRepository.save(city1); 
		
		City city2 = new City(); 
		city2.setCountry(country1);
		city2.setName("Beograd");
		city2.setPostal_code("11000");
		city2 = cityRepository.save(city2); 
		
		User user1 = new User(); 
		user1.setAddress("Zeleznicka 19");
		user1.setCity(city1);
		user1.setConfirmed_mail(true);
		user1.setContact("0631652267");
		user1.setEmail("jelena@gmail.com");
		user1.setName("Jelena");
		user1.setPassword("j");
		user1.setSurname("Jankovic");
		user1.setUsername("j");
		user1 = userRepository.save(user1); 
		
		
		
		///////////////////////////////////
		// KINEZI
		///////////////////////////////////
		
		Restaurant r1 = new Restaurant();
		r1.setId(1);
		r1.setAddress("");
		r1.setCity(city1);
		r1.setContact("0631701710");
		r1.setImageUrl("/img/dvaStapica.jpg");
		r1.setName("Dva Stapica");
		r1.setReservations(null);
		r1.setReviews(null);
		r1.setTables(new ArrayList<RestaurantTable>());
		r1.setType(RestaurantType.CHINESE);
		r1.setX(45.2564063D);
		r1.setY(19.8501625D);
		r1 = restaurantRepository.save(r1); 
		
		RestaurantTable rt1 = new RestaurantTable(); 
		rt1.setMax_seats(4);
		rt1.setNumber("1");
		rt1.setRestaurant(r1);
		rt1 = restaurantTableRepository.save(rt1); 
		
		RestaurantTable rt2 = new RestaurantTable(); 
		rt2.setMax_seats(4);
		rt2.setNumber("2");
		rt2.setRestaurant(r1);
		rt2 = restaurantTableRepository.save(rt2); 
		
		RestaurantTable rt3 = new RestaurantTable(); 
		rt3.setMax_seats(4);
		rt3.setNumber("3");
		rt3.setRestaurant(r1);
		rt3 = restaurantTableRepository.save(rt3); 
		
		r1.getTables().add(rt1);
		r1.getTables().add(rt2); 
		r1.getTables().add(rt3); 
		r1 = restaurantRepository.save(r1); 
		
		
		/////////////////////////////////////////
		
		
		Restaurant r2 = new Restaurant(); 
		r2.setAddress("");
		r2.setCity(city1);
		r2.setContact("0631701710");
		r2.setImageUrl("/img/kineskiZid.jfif");
		r2.setName("Kineski zid");
		r2.setReservations(null);
		r2.setReviews(null);
		r2.setTables(new ArrayList<RestaurantTable>());
		r2.setType(RestaurantType.CHINESE);
		r2.setX(45.2506388D);
		r2.setY(19.8386143D);
		r2 = restaurantRepository.save(r2); 
		
		RestaurantTable rt21 = new RestaurantTable(); 
		rt21.setMax_seats(4);
		rt21.setNumber("1");
		rt21.setRestaurant(r2);
		rt21 = restaurantTableRepository.save(rt21); 
		
		RestaurantTable rt22 = new RestaurantTable(); 
		rt22.setMax_seats(4);
		rt22.setNumber("2");
		rt22.setRestaurant(r2);
		rt22 = restaurantTableRepository.save(rt22); 
		
		RestaurantTable rt23 = new RestaurantTable(); 
		rt23.setMax_seats(4);
		rt23.setNumber("3");
		rt23.setRestaurant(r2);
		rt23 = restaurantTableRepository.save(rt23); 
		
		r2.getTables().add(rt21);
		r2.getTables().add(rt22); 
		r2.getTables().add(rt23); 
		r2 = restaurantRepository.save(r2); 
		
		
		//////////////////////////////////////////////
		
		
		
		Restaurant r3 = new Restaurant(); 
		r3.setAddress("");
		r3.setCity(city1);
		r3.setContact("0631701710");
		r3.setImageUrl("/img/maliLeo.jfif");
		r3.setName("Mali Leo");
		r3.setReservations(null);
		r3.setReviews(null);
		r3.setTables(new ArrayList<RestaurantTable>());
		r3.setType(RestaurantType.CHINESE);
		r3.setX(45.2602804D);
		r3.setY(19.8344229D);
		r3 = restaurantRepository.save(r3); 
		
		RestaurantTable rt31 = new RestaurantTable(); 
		rt31.setMax_seats(4);
		rt31.setNumber("1");
		rt31.setRestaurant(r3);
		rt31 = restaurantTableRepository.save(rt31); 
		
		RestaurantTable rt32 = new RestaurantTable(); 
		rt32.setMax_seats(4);
		rt32.setNumber("2");
		rt32.setRestaurant(r3);
		rt32 = restaurantTableRepository.save(rt32); 
		
		RestaurantTable rt33 = new RestaurantTable(); 
		rt33.setMax_seats(4);
		rt33.setNumber("3");
		rt33.setRestaurant(r3);
		rt33 = restaurantTableRepository.save(rt33); 
		
		r3.getTables().add(rt31);
		r3.getTables().add(rt32); 
		r3.getTables().add(rt33); 
		r3 = restaurantRepository.save(r3); 
		
		
		//////////////////////////////////////////////
		// FRANCUSKA KUHINJA
		//////////////////////////////////////////////
		
		Restaurant r4 = new Restaurant(); 
		r4.setAddress("");
		r4.setCity(city1);
		r4.setContact("0631701710");
		r4.setImageUrl("/img/laMer.jfif");
		r4.setName("La MER");
		r4.setReservations(null);
		r4.setReviews(null);
		r4.setTables(new ArrayList<RestaurantTable>());
		r4.setType(RestaurantType.FRANCHE);
		r4 = restaurantRepository.save(r4); 
		
		RestaurantTable rt41 = new RestaurantTable(); 
		rt41.setMax_seats(4);
		rt41.setNumber("1");
		rt41.setRestaurant(r4);
		rt41 = restaurantTableRepository.save(rt41); 
		
		RestaurantTable rt42 = new RestaurantTable(); 
		rt42.setMax_seats(4);
		rt42.setNumber("2");
		rt42.setRestaurant(r4);
		rt42 = restaurantTableRepository.save(rt42); 
		
		RestaurantTable rt43 = new RestaurantTable(); 
		rt43.setMax_seats(4);
		rt43.setNumber("3");
		rt43.setRestaurant(r4);
		rt43 = restaurantTableRepository.save(rt43); 
		
		r4.getTables().add(rt41);
		r4.getTables().add(rt42); 
		r4.getTables().add(rt43); 
		r4 = restaurantRepository.save(r4); 
		
		
		//////////////////////////////////////////////
		
		
		Restaurant r5 = new Restaurant(); 
		r5.setAddress("");
		r5.setCity(city1);
		r5.setContact("0631701710");
		r5.setImageUrl("/img/hedone.jfif");
		r5.setName("Hedone");
		r5.setReservations(null);
		r5.setReviews(null);
		r5.setTables(new ArrayList<RestaurantTable>());
		r5.setType(RestaurantType.FRANCHE);
		r5 = restaurantRepository.save(r5); 
		
		RestaurantTable rt51 = new RestaurantTable(); 
		rt51.setMax_seats(4);
		rt51.setNumber("1");
		rt51.setRestaurant(r5);
		rt51 = restaurantTableRepository.save(rt51); 
		
		RestaurantTable rt52 = new RestaurantTable(); 
		rt52.setMax_seats(4);
		rt52.setNumber("2");
		rt52.setRestaurant(r5);
		rt52 = restaurantTableRepository.save(rt52); 
		
		RestaurantTable rt53 = new RestaurantTable(); 
		rt53.setMax_seats(4);
		rt53.setNumber("3");
		rt53.setRestaurant(r5);
		rt53 = restaurantTableRepository.save(rt53); 
		
		r5.getTables().add(rt51);
		r5.getTables().add(rt52); 
		r5.getTables().add(rt53); 
		r5 = restaurantRepository.save(r5); 
		
		
		//////////////////////////////////////////////
		// ITALIJANSKA KUHINJA
		//////////////////////////////////////////////
		
		Restaurant r6 = new Restaurant(); 
		r6.setAddress("");
		r6.setCity(city1);
		r6.setContact("0631701710");
		r6.setImageUrl("/img/gondola.jfif");
		r6.setName("Gondola");
		r6.setReservations(null);
		r6.setReviews(null);
		r6.setTables(new ArrayList<RestaurantTable>());
		r6.setType(RestaurantType.ITALIAN);
		r6 = restaurantRepository.save(r6); 
		
		RestaurantTable rt61 = new RestaurantTable(); 
		rt61.setMax_seats(4);
		rt61.setNumber("1");
		rt61.setRestaurant(r6);
		rt61 = restaurantTableRepository.save(rt61); 
		
		RestaurantTable rt62 = new RestaurantTable(); 
		rt62.setMax_seats(4);
		rt62.setNumber("2");
		rt62.setRestaurant(r6);
		rt62 = restaurantTableRepository.save(rt62); 
		
		RestaurantTable rt63 = new RestaurantTable(); 
		rt63.setMax_seats(4);
		rt63.setNumber("3");
		rt63.setRestaurant(r6);
		rt63 = restaurantTableRepository.save(rt63); 
		
		r6.getTables().add(rt61);
		r6.getTables().add(rt62); 
		r6.getTables().add(rt63); 
		r6 = restaurantRepository.save(r6); 
		
		
		//////////////////////////////////////////////
		
		
		Restaurant r7 = new Restaurant(); 
		r7.setAddress("");
		r7.setCity(city1);
		r7.setContact("");
		r7.setImageUrl("/img/allaLanterna.jfif");
		r7.setName("alla Lanterna");
		r7.setReservations(null);
		r7.setReviews(null);
		r7.setTables(new ArrayList<RestaurantTable>());
		r7.setType(RestaurantType.ITALIAN);
		r7 = restaurantRepository.save(r7); 
		
		RestaurantTable rt71 = new RestaurantTable(); 
		rt71.setMax_seats(4);
		rt71.setNumber("1");
		rt71.setRestaurant(r7);
		rt71 = restaurantTableRepository.save(rt71); 
		
		RestaurantTable rt72 = new RestaurantTable(); 
		rt72.setMax_seats(4);
		rt72.setNumber("2");
		rt72.setRestaurant(r7);
		rt72 = restaurantTableRepository.save(rt72); 
		
		RestaurantTable rt73 = new RestaurantTable(); 
		rt73.setMax_seats(4);
		rt73.setNumber("3");
		rt73.setRestaurant(r7);
		rt73 = restaurantTableRepository.save(rt73); 
		
		r7.getTables().add(rt71);
		r7.getTables().add(rt72); 
		r7.getTables().add(rt73); 
		r7 = restaurantRepository.save(r7); 
		
		
		//////////////////////////////////////////////
		
		
		Restaurant r8 = new Restaurant(); 
		r8.setAddress("");
		r8.setCity(city1);
		r8.setContact("");
		r8.setImageUrl("/img/piatto.jfif");
		r8.setName("Piatto");
		r8.setReservations(null);
		r8.setReviews(null);
		r8.setTables(new ArrayList<RestaurantTable>());
		r8.setType(RestaurantType.ITALIAN);
		r8 = restaurantRepository.save(r8); 
		
		RestaurantTable rt81 = new RestaurantTable(); 
		rt81.setMax_seats(4);
		rt81.setNumber("1");
		rt81.setRestaurant(r8);
		rt81 = restaurantTableRepository.save(rt81); 
		
		RestaurantTable rt82 = new RestaurantTable(); 
		rt82.setMax_seats(4);
		rt82.setNumber("2");
		rt82.setRestaurant(r8);
		rt82 = restaurantTableRepository.save(rt82); 
		
		RestaurantTable rt83 = new RestaurantTable(); 
		rt83.setMax_seats(4);
		rt83.setNumber("3");
		rt83.setRestaurant(r8);
		rt83 = restaurantTableRepository.save(rt83); 
		
		r8.getTables().add(rt81);
		r8.getTables().add(rt82); 
		r8.getTables().add(rt83); 
		r8 = restaurantRepository.save(r8); 
				
		
		//////////////////////////////////////////////
        // INDIJSKA KUHINJA
		//////////////////////////////////////////////
		
		
		Restaurant r9 = new Restaurant(); 
		r9.setAddress("");
		r9.setCity(city1);
		r9.setContact("");
		r9.setImageUrl("/img/surabaya.jfif");
		r9.setName("Surabaya");
		r9.setReservations(null);
		r9.setReviews(null);
		r9.setTables(new ArrayList<RestaurantTable>());
		r9.setType(RestaurantType.INDIAN);
		r9 = restaurantRepository.save(r9); 
		
		RestaurantTable rt91 = new RestaurantTable(); 
		rt91.setMax_seats(4);
		rt91.setNumber("1");
		rt91.setRestaurant(r9);
		rt91 = restaurantTableRepository.save(rt91); 
		
		RestaurantTable rt92 = new RestaurantTable(); 
		rt92.setMax_seats(4);
		rt92.setNumber("2");
		rt92.setRestaurant(r9);
		rt92 = restaurantTableRepository.save(rt92); 
		
		RestaurantTable rt93 = new RestaurantTable(); 
		rt93.setMax_seats(4);
		rt93.setNumber("3");
		rt93.setRestaurant(r9);
		rt93 = restaurantTableRepository.save(rt93); 
		
		r9.getTables().add(rt91);
		r9.getTables().add(rt92); 
		r9.getTables().add(rt93); 
		r9 = restaurantRepository.save(r9); 
		
		
		//////////////////////////////////////////////

		
		Restaurant r10 = new Restaurant(); 
		r10.setAddress("");
		r10.setCity(city1);
		r10.setContact("");
		r10.setImageUrl("/img/realIndia.jfif");
		r10.setName("Real India");
		r10.setReservations(null);
		r10.setReviews(null);
		r10.setTables(new ArrayList<RestaurantTable>());
		r10.setType(RestaurantType.INDIAN);
		r10 = restaurantRepository.save(r10); 
		
		RestaurantTable rt101 = new RestaurantTable(); 
		rt101.setMax_seats(4);
		rt101.setNumber("1");
		rt101.setRestaurant(r10);
		rt101 = restaurantTableRepository.save(rt101); 
		
		RestaurantTable rt102 = new RestaurantTable(); 
		rt102.setMax_seats(4);
		rt102.setNumber("2");
		rt102.setRestaurant(r10);
		rt102 = restaurantTableRepository.save(rt102); 
		
		RestaurantTable rt103 = new RestaurantTable(); 
		rt103.setMax_seats(4);
		rt103.setNumber("3");
		rt103.setRestaurant(r10);
		rt103 = restaurantTableRepository.save(rt103); 
		
		r10.getTables().add(rt101);
		r10.getTables().add(rt102); 
		r10.getTables().add(rt103); 
		r10 = restaurantRepository.save(r10); 
		
		
		//////////////////////////////////////////////
		// DOMACA KUHINJA
		//////////////////////////////////////////////
		

		Restaurant r11 = new Restaurant(); 
		r11.setAddress("");
		r11.setCity(city1);
		r11.setContact("");
		r11.setImageUrl("/img/plavaFrajla.jfif");
		r11.setName("Plava frajla");
		r11.setReservations(null);
		r11.setReviews(null);
		r11.setTables(new ArrayList<RestaurantTable>());
		r11.setType(RestaurantType.SERBIAN);
		r11 = restaurantRepository.save(r11); 
		
		RestaurantTable rt111 = new RestaurantTable(); 
		rt111.setMax_seats(4);
		rt111.setNumber("1");
		rt111.setRestaurant(r11);
		rt111 = restaurantTableRepository.save(rt111); 
		
		RestaurantTable rt112 = new RestaurantTable(); 
		rt112.setMax_seats(4);
		rt112.setNumber("2");
		rt112.setRestaurant(r11);
		rt112 = restaurantTableRepository.save(rt112); 
		
		RestaurantTable rt113 = new RestaurantTable(); 
		rt113.setMax_seats(4);
		rt113.setNumber("3");
		rt113.setRestaurant(r11);
		rt113 = restaurantTableRepository.save(rt113); 
		
		r11.getTables().add(rt111);
		r11.getTables().add(rt112); 
		r11.getTables().add(rt113); 
		r11 = restaurantRepository.save(r11); 
		
		
		//////////////////////////////////////////////
		
		
		Restaurant r12 = new Restaurant(); 
		r12.setAddress("");
		r12.setCity(city1);
		r12.setContact("");
		r12.setImageUrl("/img/salas137.jfif");
		r12.setName("Salas 137");
		r12.setReservations(null);
		r12.setReviews(null);
		r12.setTables(new ArrayList<RestaurantTable>());
		r12.setType(RestaurantType.SERBIAN);
		r12 = restaurantRepository.save(r12); 
		
		RestaurantTable rt121 = new RestaurantTable(); 
		rt121.setMax_seats(4);
		rt121.setNumber("1");
		rt121.setRestaurant(r12);
		rt121 = restaurantTableRepository.save(rt121); 
		
		RestaurantTable rt122 = new RestaurantTable(); 
		rt122.setMax_seats(4);
		rt122.setNumber("2");
		rt122.setRestaurant(r12);
		rt122 = restaurantTableRepository.save(rt122); 
		
		RestaurantTable rt123 = new RestaurantTable(); 
		rt123.setMax_seats(4);
		rt123.setNumber("3");
		rt123.setRestaurant(r12);
		rt123 = restaurantTableRepository.save(rt123); 
		
		r12.getTables().add(rt121);
		r12.getTables().add(rt122); 
		r12.getTables().add(rt123); 
		r12 = restaurantRepository.save(r12); 
		
		
		//////////////////////////////////////////////
		
		// Reviews FOR 2 STAPICA

		Review rw1 = new Review();
		rw1.setComment("Actually I changed my mind. Don't like it.");
		rw1.setDate(LocalDateTime.now());
		rw1.setRatting(4);
		rw1.setRestaurant(r1);
		rw1.setUser(user1);
		rw1 = reviewRepository.save(rw1);

		Review rw2 = new Review();
		rw2.setComment("Really nice.");
		rw2.setDate(LocalDateTime.now().minusDays(4));
		rw2.setRestaurant(r1);
		rw2.setUser(user1);
		rw2 = reviewRepository.save(rw2);



		
		System.out.println("DATABASE FILLED");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
