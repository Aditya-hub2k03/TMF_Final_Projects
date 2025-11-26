package com.yogesh.moviebooking.controller;

import com.yogesh.moviebooking.model.*;
import com.yogesh.moviebooking.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@PreAuthorize("hasRole('APPLICATION_ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final MovieRepository movieRepository;
    private final PromotionRepository promotionRepository;
    private final TheatreRepository theatreRepository;

    public AdminController(UserRepository userRepository,
                           CityRepository cityRepository,
                           MovieRepository movieRepository,
                           PromotionRepository promotionRepository,
                           TheatreRepository theatreRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.movieRepository = movieRepository;
        this.promotionRepository = promotionRepository;
        this.theatreRepository = theatreRepository;
    }

    // --- Users ---

    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String roleName = body.get("role");
        return userRepository.findById(id).map(user -> {
            if (roleName != null) {
                try {
                    user.setRole(User.UserRole.valueOf(roleName));
                } catch (IllegalArgumentException ex) {
                    // ignore invalid role
                }
            }
            return ResponseEntity.ok(userRepository.save(user));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- Cities ---

    @GetMapping("/cities")
    public List<City> listCities() {
        return cityRepository.findAll();
    }

    @PostMapping("/cities")
    public City addCity(@RequestBody City city) {
        city.setId(null);
        return cityRepository.save(city);
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City incoming) {
        return cityRepository.findById(id).map(city -> {
            city.setName(incoming.getName());
            return ResponseEntity.ok(cityRepository.save(city));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        if (!cityRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cityRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- Movies ---

    @GetMapping("/movies")
    public List<Movie> adminMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("/movies")
    public Movie addMovie(@RequestBody Movie movie) {
        movie.setId(null);
        return movieRepository.save(movie);
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie incoming) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(incoming.getTitle());
            movie.setDescription(incoming.getDescription());
            movie.setPosterUrl(incoming.getPosterUrl());
            movie.setGenre(incoming.getGenre());
            movie.setReleaseDate(incoming.getReleaseDate());
            movie.setDurationMinutes(incoming.getDurationMinutes());
            return ResponseEntity.ok(movieRepository.save(movie));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- Promotions ---

    @GetMapping("/promotions")
    public List<Promotion> listPromotions() {
        return promotionRepository.findAll();
    }

    @PostMapping("/promotions")
    public Promotion addPromotion(@RequestBody Promotion promotion) {
        promotion.setId(null);
        return promotionRepository.save(promotion);
    }

    @PutMapping("/promotions/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable Long id, @RequestBody Promotion incoming) {
        return promotionRepository.findById(id).map(p -> {
            p.setCode(incoming.getCode());
            p.setDiscountPercentage(incoming.getDiscountPercentage());
            p.setStartDate(incoming.getStartDate());
            p.setEndDate(incoming.getEndDate());
            return ResponseEntity.ok(promotionRepository.save(p));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/promotions/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable Long id) {
        if (!promotionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        promotionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- Theatres overview for admin ---

    @GetMapping("/theatres")
    public List<Theatre> listTheatres() {
        return theatreRepository.findAll();
    }
}