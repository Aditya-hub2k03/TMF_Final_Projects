package com.yogesh.moviebooking.repository;

import com.yogesh.moviebooking.model.Seat;
import com.yogesh.moviebooking.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreen(Screen screen);
}