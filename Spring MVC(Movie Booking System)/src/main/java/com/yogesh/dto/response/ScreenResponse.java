package com.yogesh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenResponse {
    private int screenId;
    private int theatreId;
    private String name;
    private int totalSeats;
}
