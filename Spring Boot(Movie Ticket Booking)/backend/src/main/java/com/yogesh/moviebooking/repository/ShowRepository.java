package com.yogesh.moviebooking.repository;

import com.yogesh.moviebooking.model.Show;
import com.yogesh.moviebooking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovieAndShowTimeAfter(Movie movie, LocalDateTime now);
}