package com.yogesh.dao;

import com.yogesh.dto.request.PaymentRequest;
import com.yogesh.dto.response.PaymentResponse;
import java.util.List;

public interface PaymentDAO {
    PaymentResponse addPayment(PaymentRequest request);
    PaymentResponse getPaymentById(int paymentId);
    PaymentResponse getPaymentByBooking(int bookingId);
    List<PaymentResponse> getPaymentsByUser(int userId);
}
