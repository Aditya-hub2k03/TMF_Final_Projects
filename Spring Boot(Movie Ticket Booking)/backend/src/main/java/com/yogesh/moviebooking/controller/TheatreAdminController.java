package com.yogesh.moviebooking.controller;

import com.yogesh.moviebooking.model.*;
import com.yogesh.moviebooking.repository.*;
import com.yogesh.moviebooking.security.AppUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/theatre")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@PreAuthorize("hasRole('THEATRE_ADMIN') or hasRole('APPLICATION_ADMIN')")
public class TheatreAdminController {

    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;
    private final ShowRepository showRepository;
    private final CityRepository cityRepository;
    private final MovieRepository movieRepository;

    public TheatreAdminController(TheatreRepository theatreRepository,
                                  ScreenRepository screenRepository,
                                  ShowRepository showRepository,
                                  CityRepository cityRepository,
                                  MovieRepository movieRepository) {
        this.theatreRepository = theatreRepository;
        this.screenRepository = screenRepository;
        this.showRepository = showRepository;
        this.cityRepository = cityRepository;
        this.movieRepository = movieRepository;
    }

    // --- Theatres for current admin ---

    @GetMapping("/theatres")
    public List<Theatre> myTheatres(@AuthenticationPrincipal AppUserDetails principal) {
        User user = principal.getDomainUser();
        if (user.getRole() == User.UserRole.APPLICATION_ADMIN) {
            return theatreRepository.findAll();
        }
        return theatreRepository.findByAdmin(user);
    }

    @PostMapping("/theatres")
    public Theatre createTheatre(@AuthenticationPrincipal AppUserDetails principal,
                                 @RequestBody Map<String, Object> body) {
        User admin = principal.getDomainUser();
        Long cityId = ((Number) body.get("cityId")).longValue();
        String name = (String) body.get("name");
        String address = (String) body.get("address");
        City city = cityRepository.findById(cityId).orElseThrow();
        Theatre theatre = Theatre.builder()
                .name(name)
                .address(address)
                .city(city)
                .admin(admin)
                .build();
        return theatreRepository.save(theatre);
    }

    // --- Screens ---

    @GetMapping("/theatres/{theatreId}/screens")
    public List<Screen> screensForTheatre(@PathVariable Long theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow();
        return screenRepository.findAll().stream()
                .filter(s -> s.getTheatre().getId().equals(theatre.getId()))
                .toList();
    }

    @PostMapping("/theatres/{theatreId}/screens")
    public Screen addScreen(@PathVariable Long theatreId, @RequestBody Map<String, Object> body) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow();
        String name = (String) body.get("name");
        Integer totalSeats = ((Number) body.get("totalSeats")).intValue();
        Screen screen = Screen.builder()
                .name(name)
                .theatre(theatre)
                .totalSeats(totalSeats)
                .build();
        return screenRepository.save(screen);
    }

    @PutMapping("/screens/{screenId}")
    public ResponseEntity<Screen> updateScreen(@PathVariable Long screenId, @RequestBody Map<String, Object> body) {
        return screenRepository.findById(screenId).map(screen -> {
            if (body.containsKey("name")) {
                screen.setName((String) body.get("name"));
            }
            if (body.containsKey("totalSeats")) {
                screen.setTotalSeats(((Number) body.get("totalSeats")).intValue());
            }
            return ResponseEntity.ok(screenRepository.save(screen));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/screens/{screenId}")
    public ResponseEntity<?> deleteScreen(@PathVariable Long screenId) {
        if (!screenRepository.existsById(screenId)) {
            return ResponseEntity.notFound().build();
        }
        screenRepository.deleteById(screenId);
        return ResponseEntity.noContent().build();
    }

    // --- Shows ---

    @GetMapping("/screens/{screenId}/shows")
    public List<Show> showsForScreen(@PathVariable Long screenId) {
        Screen screen = screenRepository.findById(screenId).orElseThrow();
        return showRepository.findAll().stream()
                .filter(sh -> sh.getScreen().getId().equals(screen.getId()))
                .toList();
    }

    @PostMapping("/screens/{screenId}/shows")
    public Show addShow(@PathVariable Long screenId, @RequestBody Map<String, Object> body) {
        Screen screen = screenRepository.findById(screenId).orElseThrow();
        Long movieId = ((Number) body.get("movieId")).longValue();
        Double price = ((Number) body.get("ticketPrice")).doubleValue();
        String showTimeStr = (String) body.get("showTime");
        LocalDateTime showTime = LocalDateTime.parse(showTimeStr);
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Show show = Show.builder()
                .movie(movie)
                .screen(screen)
                .showTime(showTime)
                .ticketPrice(price)
                .build();
        return showRepository.save(show);
    }

    @PutMapping("/shows/{showId}")
    public ResponseEntity<Show> updateShow(@PathVariable Long showId, @RequestBody Map<String, Object> body) {
        return showRepository.findById(showId).map(show -> {
            if (body.containsKey("ticketPrice")) {
                show.setTicketPrice(((Number) body.get("ticketPrice")).doubleValue());
            }
            if (body.containsKey("showTime")) {
                show.setShowTime(LocalDateTime.parse((String) body.get("showTime")));
            }
            return ResponseEntity.ok(showRepository.save(show));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/shows/{showId}")
    public ResponseEntity<?> deleteShow(@PathVariable Long showId) {
        if (!showRepository.existsById(showId)) {
            return ResponseEntity.notFound().build();
        }
        showRepository.deleteById(showId);
        return ResponseEntity.noContent().build();
    }
}