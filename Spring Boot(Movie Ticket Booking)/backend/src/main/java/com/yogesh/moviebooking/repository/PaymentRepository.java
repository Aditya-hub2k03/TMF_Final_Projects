package com.yogesh.moviebooking.repository;

import com.yogesh.moviebooking.model.Payment;
import com.yogesh.moviebooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBooking(Booking booking);
}