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
public class Booking {
    private int bookingId;
    private int userId; // References User.userId
    private int showId; // References Show.showId
    private Timestamp bookingTime;
    private double totalAmount;
    private String qrCodePath;
    private String pdfPath;
}
