package com.yogesh.moviebooking.config;

import com.yogesh.moviebooking.model.*;
import com.yogesh.moviebooking.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepository,
                               CityRepository cityRepository,
                               TheatreRepository theatreRepository,
                               ScreenRepository screenRepository,
                               MovieRepository movieRepository,
                               ShowRepository showRepository,
                               SeatRepository seatRepository,
                               PromotionRepository promotionRepository,
                               ReviewRepository reviewRepository,
                               BankDetailsRepository bankDetailsRepository,
                               BookingRepository bookingRepository,
                               PaymentRepository paymentRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = User.builder()
                        .username("admin")
                        .email("admin@example.com")
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .role(User.UserRole.APPLICATION_ADMIN)
                        .build();

                User theatreAdmin = User.builder()
                        .username("theatre_admin")
                        .email("theatre@example.com")
                        .passwordHash(passwordEncoder.encode("theatre123"))
                        .role(User.UserRole.THEATRE_ADMIN)
                        .build();

                User user1 = User.builder()
                        .username("user1")
                        .email("user1@example.com")
                        .passwordHash(passwordEncoder.encode("password1"))
                        .role(User.UserRole.NORMAL_USER)
                        .build();

                User user2 = User.builder()
                        .username("user2")
                        .email("user2@example.com")
                        .passwordHash(passwordEncoder.encode("password2"))
                        .role(User.UserRole.NORMAL_USER)
                        .build();

                userRepository.saveAll(List.of(admin, theatreAdmin, user1, user2));
            }

            // Reload users to ensure we have managed entities
            List<User> allUsers = userRepository.findAll();
            User adminUser = allUsers.stream().filter(u -> u.getRole() == User.UserRole.APPLICATION_ADMIN).findFirst().orElse(null);
            User theatreAdminUser = allUsers.stream().filter(u -> u.getRole() == User.UserRole.THEATRE_ADMIN).findFirst().orElse(null);
            User normalUser1 = allUsers.stream().filter(u -> "user1".equals(u.getUsername())).findFirst().orElse(null);
            User normalUser2 = allUsers.stream().filter(u -> "user2".equals(u.getUsername())).findFirst().orElse(null);

            if (cityRepository.count() == 0) {
                City city1 = City.builder().name("New York").build();
                City city2 = City.builder().name("Los Angeles").build();
                City city3 = City.builder().name("Chicago").build();
                City city4 = City.builder().name("Houston").build();
                City city5 = City.builder().name("Phoenix").build();
                cityRepository.saveAll(List.of(city1, city2, city3, city4, city5));

                Theatre theatre1 = Theatre.builder()
                        .name("Cineplex Downtown")
                        .address("123 Main St")
                        .city(city1)
                        .admin(theatreAdminUser)
                        .build();
                Theatre theatre2 = Theatre.builder()
                        .name("Sunset Screens")
                        .address("456 Sunset Blvd")
                        .city(city2)
                        .admin(theatreAdminUser)
                        .build();
                theatreRepository.saveAll(List.of(theatre1, theatre2));

                Screen screen1 = Screen.builder()
                        .name("Screen 1")
                        .theatre(theatre1)
                        .totalSeats(50)
                        .build();
                Screen screen2 = Screen.builder()
                        .name("Screen 2")
                        .theatre(theatre2)
                        .totalSeats(40)
                        .build();
                screenRepository.saveAll(List.of(screen1, screen2));

                Movie movie1 = Movie.builder()
                        .title("The Dark Knight")
                        .description("A superhero movie about Batman.")
                        .genre("Action")
                        .posterUrl("https://m.media-amazon.com/images/I/51EbJjlLg8L._AC_UF894,1000_QL80_.jpg")
                        .releaseDate(LocalDate.of(2008, 7, 18))
                        .durationMinutes(152)
                        .build();

                Movie movie2 = Movie.builder()
                        .title("Inception")
                        .description("A sci-fi thriller about dreams within dreams.")
                        .genre("Sci-Fi")
                        .posterUrl("https://m.media-amazon.com/images/I/51s+8ON4uBL._AC_.jpg")
                        .releaseDate(LocalDate.of(2010, 7, 16))
                        .durationMinutes(148)
                        .build();

                Movie movie3 = Movie.builder()
                        .title("The Shawshank Redemption")
                        .description("A drama about hope and friendship inside a prison.")
                        .genre("Drama")
                        .posterUrl("https://m.media-amazon.com/images/I/51NiGlapXlL._AC_.jpg")
                        .releaseDate(LocalDate.of(1994, 9, 22))
                        .durationMinutes(142)
                        .build();

                
Movie movie4 = Movie.builder()
        .title("Pulp Fiction")
        .description("A crime movie with intertwining stories.")
        .genre("Crime")
        .posterUrl("https://m.media-amazon.com/images/I/71c05lTE03L._AC_UF894,1000_QL80_.jpg")
        .releaseDate(LocalDate.of(1994, 10, 14))
        .durationMinutes(154)
        .build();

Movie movie5 = Movie.builder()
        .title("The Godfather")
        .description("A crime saga about a mafia family.")
        .genre("Crime")
        .posterUrl("https://m.media-amazon.com/images/I/51rOnIjLqzL._AC_.jpg")
        .releaseDate(LocalDate.of(1972, 3, 24))
        .durationMinutes(175)
        .build();

movieRepository.saveAll(List.of(movie1, movie2, movie3, movie4, movie5));

                Show show1 = Show.builder()
                        .movie(movie1)
                        .screen(screen1)
                        .showTime(LocalDateTime.now().plusHours(2))
                        .ticketPrice(12.99)
                        .build();
                Show show2 = Show.builder()
                        .movie(movie2)
                        .screen(screen1)
                        .showTime(LocalDateTime.now().plusHours(5))
                        .ticketPrice(11.99)
                        .build();
                Show show3 = Show.builder()
                        .movie(movie3)
                        .screen(screen2)
                        .showTime(LocalDateTime.now().plusHours(3))
                        .ticketPrice(10.99)
                        .build();
                showRepository.saveAll(List.of(show1, show2, show3));

                // Seats
                for (Screen screen : List.of(screen1, screen2)) {
                    for (int i = 1; i <= screen.getTotalSeats(); i++) {
                        Seat seat = Seat.builder()
                                .screen(screen)
                                .seatNumber("S" + i)
                                .available(true)
                                .build();
                        seatRepository.save(seat);
                    }
                }

                // Promotions
                Promotion p1 = Promotion.builder()
                        .code("WELCOME20")
                        .discountPercentage(20.0)
                        .startDate(LocalDate.now().minusDays(1))
                        .endDate(LocalDate.now().plusMonths(1))
                        .build();
                Promotion p2 = Promotion.builder()
                        .code("SUMMER15")
                        .discountPercentage(15.0)
                        .startDate(LocalDate.now().minusMonths(1))
                        .endDate(LocalDate.now().plusMonths(2))
                        .build();
                promotionRepository.saveAll(List.of(p1, p2));

                // Simple reviews
                if (normalUser1 != null && normalUser2 != null) {
                    Review r1 = Review.builder()
                            .movie(movie1)
                            .user(normalUser1)
                            .rating(5)
                            .comment("Awesome movie!")
                            .build();
                    Review r2 = Review.builder()
                            .movie(movie2)
                            .user(normalUser2)
                            .rating(4)
                            .comment("Great action scenes.")
                            .build();
                    reviewRepository.saveAll(List.of(r1, r2));
                }
            }

            // Sample bank details for user1
            if (normalUser1 != null && bankDetailsRepository.findFirstByUser(normalUser1).isEmpty()) {
                BankDetails bd = BankDetails.builder()
                        .user(normalUser1)
                        .cardNumber("4111111111111111")
                        .cardHolderName("User One")
                        .expiryDate("12/2030")
                        .cvv("123")
                        .build();
                bankDetailsRepository.save(bd);
            }

            // Sample bookings and payments if not present
            if (bookingRepository.count() == 0 && normalUser1 != null) {
                List<Show> shows = showRepository.findAll();
                if (!shows.isEmpty()) {
                    Show show = shows.get(0);
                    Booking booking = Booking.builder()
                            .user(normalUser1)
                            .show(show)
                            .totalAmount(show.getTicketPrice() * 2)
                            .build();
                    bookingRepository.save(booking);

                    Payment payment = Payment.builder()
                            .booking(booking)
                            .amount(booking.getTotalAmount())
                            .paymentMethod("Credit Card")
                            .transactionId("TXN123456789")
                            .build();
                    paymentRepository.save(payment);
                }
            }
        };
    }
}