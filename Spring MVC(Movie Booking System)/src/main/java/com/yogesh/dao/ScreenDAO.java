package com.yogesh.dao;

import com.yogesh.dto.request.ScreenRequest;
import com.yogesh.dto.response.ScreenResponse;
import java.util.List;

public interface ScreenDAO {
    ScreenResponse addScreen(ScreenRequest request);
    ScreenResponse getScreenById(int screenId);
    List<ScreenResponse> getScreensByTheatre(int theatreId);
    boolean updateScreen(ScreenRequest request, int screenId);
    boolean deleteScreen(int screenId);
}
