package com.yogesh.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.yogesh.dao.BookingDAO;
import com.yogesh.dao.SeatDAO;
import com.yogesh.dto.request.BookingRequest;
import com.yogesh.dto.response.BookingResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;
    private final SeatDAO seatDAO;
    private static final String BASE_DIR = "C:\\Users\\yogee\\eclipse-workspace\\MovieBookingMVC";


    public BookingServiceImpl(BookingDAO bookingDAO, SeatDAO seatDAO) {
        this.bookingDAO = bookingDAO;
        this.seatDAO = seatDAO;
    }

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        // Validate request
        if (request.getUserId() <= 0 || request.getShowId() <= 0 || request.getSeatIds() == null || request.getSeatIds().isEmpty()) {
            throw new IllegalArgumentException("User ID, show ID, and seat IDs are required.");
        }
        // Check seat availability
        for (int seatId : request.getSeatIds()) {
            if (seatDAO.getSeatById(seatId).isBooked()) {
                throw new IllegalStateException("Seat " + seatId + " is already booked.");
            }
        }
        // Create booking
        BookingResponse booking = bookingDAO.createBooking(request);
        // Update seat status
        for (int seatId : request.getSeatIds()) {
            seatDAO.updateSeatStatus(seatId, true);
        }
        return booking;
    }

    @Override
    public BookingResponse getBookingById(int bookingId) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Invalid booking ID.");
        }
        return bookingDAO.getBookingById(bookingId);
    }

    @Override
    public List<BookingResponse> getBookingsByUser(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        return bookingDAO.getBookingsByUser(userId);
    }

    @Override
    public List<BookingResponse> getBookingsByShow(int showId) {
        if (showId <= 0) {
            throw new IllegalArgumentException("Invalid show ID.");
        }
        return bookingDAO.getBookingsByShow(showId);
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Invalid booking ID.");
        }
        // Get booking details
        BookingResponse booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            throw new IllegalStateException("Booking not found.");
        }
        // Cancel booking
        boolean canceled = bookingDAO.cancelBooking(bookingId);
        if (canceled) {
            // Free up seats
            List<Integer> seatIds = getSeatIdsByBooking(bookingId);
            for (int seatId : seatIds) {
                seatDAO.updateSeatStatus(seatId, false);
            }
        }
        return canceled;
    }

    // Helper method to get seat IDs by booking ID
    private List<Integer> getSeatIdsByBooking(int bookingId) {
        // Implement logic to fetch seat IDs for a booking
        return List.of(); // Placeholder
    }
    
    @Override
    public BookingResponse generateTicket(int bookingId) {
        BookingResponse booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found.");
        }
        // Generate QR code
        String qrCodePath = generateQRCode(booking);
        if (qrCodePath == null) {
            throw new RuntimeException("Failed to generate QR code.");
        }
        // Generate PDF
        String pdfPath = generatePDF(booking, qrCodePath);
        if (pdfPath == null) {
            throw new RuntimeException("Failed to generate PDF.");
        }
        // Update booking with paths
        bookingDAO.updateTicketPaths(bookingId, qrCodePath, pdfPath);
        return bookingDAO.getBookingById(bookingId);
    }


    private String generateQRCode(BookingResponse booking) {
        try {
            String content = "Booking ID: " + booking.getBookingId() +
                             "\nShow ID: " + booking.getShowId() +
                             "\nUser ID: " + booking.getUserId() +
                             "\nBooking Time: " + booking.getBookingTime();

            Path qrCodesDir = Paths.get(BASE_DIR, "qr_codes");
            if (!Files.exists(qrCodesDir)) {
                Files.createDirectories(qrCodesDir);
            }

            String filePath = qrCodesDir.resolve("booking_" + booking.getBookingId() + ".png").toString();

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, 300, 300);
            MatrixToImageWriter.writeToPath(matrix, "PNG", Paths.get(filePath));
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

private String generatePDF(BookingResponse booking, String qrCodePath) {
    try {
    	Path ticketsDir = Paths.get(BASE_DIR, "tickets");
        if (!Files.exists(ticketsDir)) {
            Files.createDirectories(ticketsDir);
        }

        String filePath = ticketsDir.resolve("booking_" + booking.getBookingId() + ".pdf").toString();

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Add content
        document.add(new Paragraph("Movie Ticket"));
        document.add(new Paragraph("Booking ID: " + booking.getBookingId()));
        document.add(new Paragraph("Show ID: " + booking.getShowId()));
        document.add(new Paragraph("User ID: " + booking.getUserId()));
        document.add(new Paragraph("Booking Time: " + booking.getBookingTime()));
        document.add(new Paragraph("Total Amount: $" + booking.getTotalAmount()));

        // Add QR code
        Image qrImage = Image.getInstance(qrCodePath);
        document.add(qrImage);
        document.close();
        return filePath;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
}
