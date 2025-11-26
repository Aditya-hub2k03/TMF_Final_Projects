package com.yogesh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private int paymentId;
    private int bookingId;
    private double amount;
    private String paymentMethod;
    private Timestamp paymentTime;
    private String transactionId;
}
