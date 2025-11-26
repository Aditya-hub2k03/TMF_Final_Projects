package com.yogesh.moviebooking.controller;

import com.yogesh.moviebooking.model.Movie;
import com.yogesh.moviebooking.model.Review;
import com.yogesh.moviebooking.repository.MovieRepository;
import com.yogesh.moviebooking.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MovieController {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    public MovieController(MovieRepository movieRepository, ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        return movieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/reviews")
    public List<Review> getReviews(@PathVariable Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        return reviewRepository.findByMovie(movie);
    }
}