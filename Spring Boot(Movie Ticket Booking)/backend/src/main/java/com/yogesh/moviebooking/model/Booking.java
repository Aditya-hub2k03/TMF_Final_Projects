package com.yogesh.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "show_id")
    private Show show;

    @Column(name = "booking_time")
    private Instant bookingTime;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "qr_code_path")
    private String qrCodePath;

    @Column(name = "pdf_path")
    private String pdfPath;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BookingSeat> seats;

    @PrePersist
    public void prePersist() {
        this.bookingTime = Instant.now();
    }
}