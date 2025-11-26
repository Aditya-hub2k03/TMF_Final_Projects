package com.yogesh.dao;

import com.yogesh.dto.request.PaymentRequest;
import com.yogesh.dto.response.PaymentResponse;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public PaymentResponse addPayment(PaymentRequest request) {
        String sql = "INSERT INTO payments (booking_id, amount, payment_method, transaction_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, request.getBookingId());
            pstmt.setDouble(2, request.getAmount());
            pstmt.setString(3, request.getPaymentMethod());
            pstmt.setString(4, request.getTransactionId());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return getPaymentById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PaymentResponse getPaymentById(int paymentId) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapPaymentToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PaymentResponse getPaymentByBooking(int bookingId) {
        String sql = "SELECT * FROM payments WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapPaymentToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PaymentResponse> getPaymentsByUser(int userId) {
        List<PaymentResponse> payments = new ArrayList<>();
        String sql = "SELECT p.* FROM payments p JOIN bookings b ON p.booking_id = b.booking_id WHERE b.user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                payments.add(mapPaymentToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    // Helper method to map ResultSet to PaymentResponse
    private PaymentResponse mapPaymentToResponse(ResultSet rs) throws SQLException {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(rs.getInt("payment_id"));
        response.setBookingId(rs.getInt("booking_id"));
        response.setAmount(rs.getDouble("amount"));
        response.setPaymentMethod(rs.getString("payment_method"));
        response.setPaymentTime(rs.getTimestamp("payment_time"));
        response.setTransactionId(rs.getString("transaction_id"));
        return response;
    }
}
