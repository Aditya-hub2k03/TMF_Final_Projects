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
public class Review {
    private int reviewId;
    private int userId;   // References User.userId
    private int movieId;  // References Movie.movieId
    private int rating;   // e.g., 1-5 stars
    private String comment;
    private Timestamp createdAt;
}
