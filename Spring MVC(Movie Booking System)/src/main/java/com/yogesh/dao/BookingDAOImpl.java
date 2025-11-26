package com.yogesh.dao;

import com.yogesh.dto.request.BookingRequest;
import com.yogesh.dto.response.BookingResponse;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class BookingDAOImpl implements BookingDAO {

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        String sql = "INSERT INTO bookings (user_id, show_id, total_amount) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            double totalAmount = calculateTotalAmount(request.getShowId(), request.getSeatIds().size());
            pstmt.setInt(1, request.getUserId());
            pstmt.setInt(2, request.getShowId());
            pstmt.setDouble(3, totalAmount);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int bookingId = rs.getInt(1);
                updateSeatBookings(bookingId, request.getSeatIds());
                return getBookingById(bookingId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BookingResponse getBookingById(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapBookingToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookingResponse> getBookingsByUser(int userId) {
        List<BookingResponse> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapBookingToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<BookingResponse> getBookingsByShow(int showId) {
        List<BookingResponse> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE show_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, showId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapBookingToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to BookingResponse
    private BookingResponse mapBookingToResponse(ResultSet rs) throws SQLException {
        BookingResponse response = new BookingResponse();
        response.setBookingId(rs.getInt("booking_id"));
        response.setUserId(rs.getInt("user_id"));
        response.setShowId(rs.getInt("show_id"));
        response.setBookingTime(rs.getTimestamp("booking_time"));
        response.setTotalAmount(rs.getDouble("total_amount"));
        response.setQrCodePath(rs.getString("qr_code_path"));
        response.setPdfPath(rs.getString("pdf_path"));
        return response;
    }

    // Helper method to calculate total amount
    private double calculateTotalAmount(int showId, int seatCount) {
        String sql = "SELECT price FROM shows WHERE show_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, showId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price") * seatCount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Helper method to update seat bookings
    private void updateSeatBookings(int bookingId, List<Integer> seatIds) {
        String sql = "INSERT INTO booking_seats (booking_id, seat_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int seatId : seatIds) {
                pstmt.setInt(1, bookingId);
                pstmt.setInt(2, seatId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean updateTicketPaths(int bookingId, String qrCodePath, String pdfPath) {
        String sql = "UPDATE bookings SET qr_code_path = ?, pdf_path = ? WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, qrCodePath);
            pstmt.setString(2, pdfPath);
            pstmt.setInt(3, bookingId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
