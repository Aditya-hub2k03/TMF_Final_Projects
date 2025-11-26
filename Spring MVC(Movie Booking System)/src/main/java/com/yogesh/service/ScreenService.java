package com.yogesh.service;

import com.yogesh.dto.request.ScreenRequest;
import com.yogesh.dto.response.ScreenResponse;
import java.util.List;

public interface ScreenService {
    ScreenResponse addScreen(ScreenRequest request);
    ScreenResponse getScreenById(int screenId);
    List<ScreenResponse> getScreensByTheatre(int theatreId);
    boolean updateScreen(ScreenRequest request, int screenId);
    boolean deleteScreen(int screenId);
}
