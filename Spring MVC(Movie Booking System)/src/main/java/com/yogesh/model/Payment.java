package com.yogesh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private int paymentId;
    private int bookingId; // References Booking.bookingId
    private double amount;
    private String paymentMethod; // e.g., "Credit Card", "UPI", "Net Banking"
    private Timestamp paymentTime;
    private String transactionId;
}
