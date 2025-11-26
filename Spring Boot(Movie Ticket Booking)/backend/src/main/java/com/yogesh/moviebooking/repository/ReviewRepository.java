package com.yogesh.moviebooking.repository;

import com.yogesh.moviebooking.model.Review;
import com.yogesh.moviebooking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovie(Movie movie);
}