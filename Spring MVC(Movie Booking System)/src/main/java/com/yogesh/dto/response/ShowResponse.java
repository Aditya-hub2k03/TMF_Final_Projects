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
public class ShowResponse {
    private int showId;
    private int movieId;
    private String movieTitle;
    private int theatreId;
    private String theatreName;
    private int screenId;
    private String screenName;
    private Timestamp startTime;
    private Timestamp endTime;
    private double price;
}
