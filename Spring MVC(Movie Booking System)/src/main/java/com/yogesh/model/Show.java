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
public class Show {
    private int showId;
    private int movieId; // References Movie.movieId
    private int screenId; // References Screen.screenId
    private Timestamp startTime;
    private Timestamp endTime;
    private double price;
}
