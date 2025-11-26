package com.yogesh.moviebooking.controller;

import com.yogesh.moviebooking.model.Movie;
import com.yogesh.moviebooking.model.Screen;
import com.yogesh.moviebooking.model.Seat;
import com.yogesh.moviebooking.model.Show;
import com.yogesh.moviebooking.repository.MovieRepository;
import com.yogesh.moviebooking.repository.ScreenRepository;
import com.yogesh.moviebooking.repository.SeatRepository;
import com.yogesh.moviebooking.repository.ShowRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ShowSeatController {

    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;

    public ShowSeatController(ShowRepository showRepository,
                              SeatRepository seatRepository,
                              MovieRepository movieRepository,
                              ScreenRepository screenRepository) {
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
    }

    @GetMapping("/movies/{movieId}/shows")
    public List<Show> showsForMovie(@PathVariable Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        return showRepository.findByMovieAndShowTimeAfter(movie, LocalDateTime.now().minusHours(3));
    }

    @GetMapping("/screens/{screenId}/seats")
    public List<Seat> seatsForScreen(@PathVariable Long screenId) {
        Screen screen = screenRepository.findById(screenId).orElseThrow();
        return seatRepository.findByScreen(screen);
    }
}