package com.yogesh.moviebooking.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id")
    @JsonIgnore
    private Booking booking;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod; // e.g., Credit Card, UPI

    @Column(name = "payment_time")
    private Instant paymentTime;

    @Column(name = "transaction_id", nullable = false, length = 100)
    private String transactionId;

    @PrePersist
    public void prePersist() {
        if (paymentTime == null) {
            paymentTime = Instant.now();
        }
    }
}