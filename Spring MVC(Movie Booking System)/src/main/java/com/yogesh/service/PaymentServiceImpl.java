package com.yogesh.service;

import com.yogesh.dao.PaymentDAO;
import com.yogesh.dto.request.PaymentRequest;
import com.yogesh.dto.response.PaymentResponse;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;

    public PaymentServiceImpl(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @Override
    public PaymentResponse addPayment(PaymentRequest request) {
        // Validate request
        if (request.getBookingId() <= 0 || request.getAmount() <= 0 || request.getPaymentMethod() == null || request.getTransactionId() == null) {
            throw new IllegalArgumentException("Booking ID, amount, payment method, and transaction ID are required.");
        }
        return paymentDAO.addPayment(request);
    }

    @Override
    public PaymentResponse getPaymentById(int paymentId) {
        if (paymentId <= 0) {
            throw new IllegalArgumentException("Invalid payment ID.");
        }
        return paymentDAO.getPaymentById(paymentId);
    }

    @Override
    public PaymentResponse getPaymentByBooking(int bookingId) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Invalid booking ID.");
        }
        return paymentDAO.getPaymentByBooking(bookingId);
    }

    @Override
    public List<PaymentResponse> getPaymentsByUser(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        return paymentDAO.getPaymentsByUser(userId);
    }
}
