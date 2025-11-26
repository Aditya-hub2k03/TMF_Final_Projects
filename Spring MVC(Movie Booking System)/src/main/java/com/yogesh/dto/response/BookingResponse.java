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
public class BookingResponse {
    private int bookingId;
    private int userId;
    private int showId;
    private Timestamp bookingTime;
    private double totalAmount;
    private String qrCodePath;
    private String pdfPath;
}
