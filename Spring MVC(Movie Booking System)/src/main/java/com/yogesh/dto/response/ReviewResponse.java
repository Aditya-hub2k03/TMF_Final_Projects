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
public class ReviewResponse {
    private int reviewId;
    private int userId;
    private String username;
    private int movieId;
    private int rating;
    private String comment;
    private Timestamp createdAt;
}

