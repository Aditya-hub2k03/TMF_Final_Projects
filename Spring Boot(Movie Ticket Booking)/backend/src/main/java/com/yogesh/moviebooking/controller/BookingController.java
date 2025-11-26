package com.yogesh.moviebooking.controller;

import com.yogesh.moviebooking.model.*;
import com.yogesh.moviebooking.repository.*;
import com.yogesh.moviebooking.security.AppUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public BookingController(BookingRepository bookingRepository,
                             ShowRepository showRepository,
                             SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    @GetMapping
    public List<Booking> myBookings(@AuthenticationPrincipal AppUserDetails principal) {
        User user = principal.getDomainUser();
        return bookingRepository.findByUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@AuthenticationPrincipal AppUserDetails principal,
                                              @PathVariable Long id) {
        User user = principal.getDomainUser();
        return bookingRepository.findById(id)
                .filter(b -> b.getUser().getId().equals(user.getId()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@AuthenticationPrincipal AppUserDetails principal,
                                           @RequestBody Map<String, Object> body) {
        User user = principal.getDomainUser();
        Number showIdNum = (Number) body.get("showId");
        @SuppressWarnings("unchecked")
        List<String> seatIdsRaw = (List<String>) body.get("seatIds");
        if (showIdNum == null || seatIdsRaw == null || seatIdsRaw.isEmpty()) {
            return ResponseEntity.badRequest().body("showId and seatIds are required");
        }
        Long showId = showIdNum.longValue();
        Show show = showRepository.findById(showId).orElseThrow();

        List<Long> seatIds = new ArrayList<>();
        for (String s : seatIdsRaw) {
            seatIds.add(Long.parseLong(s));
        }

        List<Seat> seats = seatRepository.findAllById(seatIds);
        double totalAmount = seats.size() * show.getTicketPrice();

        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .totalAmount(totalAmount)
                .build();

        List<BookingSeat> bookingSeats = new ArrayList<>();
        for (Seat seat : seats) {
            BookingSeat bs = BookingSeat.builder()
                    .booking(booking)
                    .seat(seat)
                    .build();
            bookingSeats.add(bs);
            seat.setAvailable(false);
        }
        booking.setSeats(bookingSeats);

        booking = bookingRepository.save(booking);

        // Mock QR and PDF paths (no real file generation)
        booking.setQrCodePath("/qr_codes/booking_" + booking.getId() + ".png");
        booking.setPdfPath("/tickets/booking_" + booking.getId() + ".pdf");
        booking = bookingRepository.save(booking);

        seatRepository.saveAll(seats);

        return ResponseEntity.ok(booking);
    }
}