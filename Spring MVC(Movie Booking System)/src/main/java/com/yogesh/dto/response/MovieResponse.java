package com.yogesh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private int movieId;
    private String title;
    private String description;
    private int durationMinutes;
    private Date releaseDate;
    private String imageUrl;
    private String language;
}
