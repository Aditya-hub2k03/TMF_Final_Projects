package com.yogesh.moviebooking.repository;

import com.yogesh.moviebooking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}