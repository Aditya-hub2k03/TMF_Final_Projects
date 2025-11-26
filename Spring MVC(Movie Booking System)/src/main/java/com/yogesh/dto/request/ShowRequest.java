package com.yogesh.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowRequest {
    private int movieId;
    private int screenId;
    private Timestamp startTime;
    private Timestamp endTime;
    private double price;
}

