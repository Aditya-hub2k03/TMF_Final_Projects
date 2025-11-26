package com.yogesh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Screen {
    private int screenId;
    private int theatreId; // References Theatre.theatreId
    private String name;
    private int totalSeats;
}
