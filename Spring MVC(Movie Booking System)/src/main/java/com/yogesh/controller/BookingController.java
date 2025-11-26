package com.yogesh.controller;

import com.yogesh.dto.request.BookingRequest;
import com.yogesh.dto.response.*;
import com.yogesh.service.BookingService;
import com.yogesh.service.SeatService;
import com.yogesh.service.ShowService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {
	

    @Autowired
    private SeatService seatService;

    private final BookingService bookingService;
    private final ShowService showService;

    public BookingController(BookingService bookingService, ShowService showService) {
        this.bookingService = bookingService;
        this.showService = showService;
    }

    @GetMapping("/select-show/{movieId}")
    public ModelAndView selectShow(@PathVariable int movieId) {
        ModelAndView mav = new ModelAndView("booking/select_show");
        List<ShowResponse> shows = showService.getShowsByMovie(movieId);
        mav.addObject("shows", shows);
        mav.addObject("movieId", movieId);
        return mav;
    }

    @GetMapping("/select-seats/{showId}")
    public ModelAndView selectSeats(@PathVariable int showId, HttpSession session) {
        ModelAndView mav = new ModelAndView("booking/select_seats");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            mav.setViewName("redirect:/user/login");
        } else {
            ShowResponse show = showService.getShowById(showId);
            if (show == null) {
                mav.setViewName("redirect:/movie/list?error=InvalidShow");
                return mav;
            }
            List<SeatResponse> seats = seatService.getSeatsByScreen(show.getScreenId());
            mav.addObject("show", show);
            mav.addObject("seats", seats);
            mav.addObject("userId", user.getUserId());
        }
        return mav;
    }



    @PostMapping("/book")
    public ModelAndView bookSeats(@ModelAttribute BookingRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            mav.setViewName("redirect:/user/login");
        } else {
            request.setUserId(user.getUserId());
            try {
                BookingResponse booking = bookingService.createBooking(request);
                mav.setViewName("redirect:/booking/confirmation/" + booking.getBookingId());
            } catch (Exception e) {
                mav.setViewName("redirect:/booking/select-seats/" + request.getShowId() + "?error=" + e.getMessage());
            }
        }
        return mav;
    }

    @GetMapping("/confirmation/{bookingId}")
    public ModelAndView bookingConfirmation(@PathVariable int bookingId, HttpSession session) {
        ModelAndView mav = new ModelAndView("booking/confirmation");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            mav.setViewName("redirect:/user/login");
        } else {
            BookingResponse booking = bookingService.getBookingById(bookingId);
            if (booking != null && booking.getUserId() == user.getUserId()) {
                mav.addObject("booking", booking);
            } else {
                mav.setViewName("redirect:/");
            }
        }
        return mav;
    }

    @GetMapping("/my-bookings")
    public ModelAndView myBookings(HttpSession session) {
        ModelAndView mav = new ModelAndView("booking/my_bookings");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            mav.setViewName("redirect:/user/login");
        } else {
            List<BookingResponse> bookings = bookingService.getBookingsByUser(user.getUserId());
            mav.addObject("bookings", bookings);
        }
        return mav;
    }
    @GetMapping("/download-ticket/{bookingId}")
    public void downloadTicket(@PathVariable int bookingId, HttpServletResponse response, HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            throw new IllegalStateException("User not logged in.");
        }
        BookingResponse booking = bookingService.getBookingById(bookingId);
        if (booking == null || booking.getUserId() != user.getUserId()) {
            throw new IllegalStateException("Booking not found or not authorized.");
        }

        // Generate ticket if not already generated
        if (booking.getPdfPath() == null) {
            booking = bookingService.generateTicket(bookingId);
            if (booking.getPdfPath() == null) {
                throw new RuntimeException("Failed to generate ticket.");
            }
        }

        // Download PDF
        try {
            Path filePath = Paths.get(booking.getPdfPath());
            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("Ticket file not found: " + booking.getPdfPath());
            }
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + filePath.getFileName().toString());
            Files.copy(filePath, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to download ticket.", e);
        }
    }



}
