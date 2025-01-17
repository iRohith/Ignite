package abc.ignite;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import abc.ignite.dto.BookingRequestDTO;
import abc.ignite.dto.ClassRequestDTO;
import abc.ignite.dto.UserRequestDTO;
import abc.ignite.service.BookingService;
import abc.ignite.service.ClassService;
import abc.ignite.service.UserService;

@SpringBootApplication
public class IgniteApplication {

	final static boolean DEMO_MODE = true;

	public static void main(String[] args) {
		SpringApplication.run(IgniteApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(
		UserService userService,
		ClassService classService,
		BookingService bookingService
	){
		return (_) -> {
			if (!DEMO_MODE) return;
			
			userService.createNewUser(new UserRequestDTO(
				"admin",
				"Admin 0",
				"admin"
			));

			userService.createNewUser(new UserRequestDTO(
				"user1",
				"User 1",
				"user"
			));

			userService.createNewUser(new UserRequestDTO(
				"user2",
				"User 2",
				"user"
			));

			classService.createNewClass(new ClassRequestDTO(
				"pilates",
				"Pilates",
				LocalDate.parse("2025-02-01"),
				LocalDate.parse("2025-02-20"),
				LocalTime.parse("10:00"),
				60,
				20
			));

			classService.createNewClass(new ClassRequestDTO(
				"lifting",
				"Lifting",
				LocalDate.parse("2025-02-15"),
				LocalDate.parse("2025-03-20"),
				LocalTime.parse("10:30"),
				60,
				20
			));

			bookingService.createNewBooking(new BookingRequestDTO("admin", "pilates", LocalDate.parse("2025-02-05")));
			bookingService.createNewBooking(new BookingRequestDTO("admin", "pilates", LocalDate.parse("2025-02-06")));
			
			bookingService.createNewBooking(new BookingRequestDTO("user1", "lifting", LocalDate.parse("2025-02-05")));
			bookingService.createNewBooking(new BookingRequestDTO("user1", "lifting", LocalDate.parse("2025-02-06")));
			bookingService.createNewBooking(new BookingRequestDTO("user1", "lifting", LocalDate.parse("2025-02-07")));
			bookingService.createNewBooking(new BookingRequestDTO("user1", "lifting", LocalDate.parse("2025-02-08")));
			bookingService.createNewBooking(new BookingRequestDTO("user1", "lifting", LocalDate.parse("2025-02-09")));
		};
	}
}
