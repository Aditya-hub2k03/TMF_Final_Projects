package com.yogesh.moviebooking.repository;

import com.yogesh.moviebooking.model.Booking;
import com.yogesh.moviebooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
}