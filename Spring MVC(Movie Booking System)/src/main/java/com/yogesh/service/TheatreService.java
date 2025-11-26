package com.yogesh.service;

import com.yogesh.dto.request.TheatreRequest;
import com.yogesh.dto.response.TheatreResponse;
import java.util.List;


public interface TheatreService {
    TheatreResponse addTheatre(TheatreRequest request);
    TheatreResponse getTheatreById(int theatreId);
    List<TheatreResponse> getAllTheatres();
    List<TheatreResponse> getTheatresByCity(String city);
    boolean updateTheatre(TheatreRequest request, int theatreId);
    boolean deleteTheatre(int theatreId);
    List<TheatreResponse> getTheatresByAdmin(int adminId);

}
