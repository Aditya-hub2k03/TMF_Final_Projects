package com.yogesh.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private int bookingId;
    private double amount;
    private String paymentMethod; // "Credit Card", "UPI", etc.
    private String transactionId;
}
