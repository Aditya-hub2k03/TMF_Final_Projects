package com.yogesh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    private int seatId;
    private int screenId; // References Screen.screenId
    private String seatNumber;
    private boolean isBooked;
}
