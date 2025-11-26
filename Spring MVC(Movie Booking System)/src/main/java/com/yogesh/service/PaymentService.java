package com.yogesh.service;

import com.yogesh.dto.request.PaymentRequest;
import com.yogesh.dto.response.PaymentResponse;
import java.util.List;

public interface PaymentService {
    PaymentResponse addPayment(PaymentRequest request);
    PaymentResponse getPaymentById(int paymentId);
    PaymentResponse getPaymentByBooking(int bookingId);
    List<PaymentResponse> getPaymentsByUser(int userId);
}
