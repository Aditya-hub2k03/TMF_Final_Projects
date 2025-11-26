package com.yogesh.service;

import com.yogesh.dao.ShowDAO;
import com.yogesh.dto.request.ShowRequest;
import com.yogesh.dto.response.ShowResponse;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class ShowServiceImpl implements ShowService {

    private final ShowDAO showDAO;

    public ShowServiceImpl(ShowDAO showDAO) {
        this.showDAO = showDAO;
    }

    @Override
    public ShowResponse addShow(ShowRequest request) {
        // Validate request
        if (request.getMovieId() <= 0 || request.getScreenId() <= 0 || request.getStartTime() == null ||
            request.getEndTime() == null || request.getPrice() <= 0) {
            throw new IllegalArgumentException("Movie ID, screen ID, start time, end time, and price are required.");
        }
        return showDAO.addShow(request);
    }

    @Override
    public ShowResponse getShowById(int showId) {
        return showDAO.getShowById(showId);
    }


    @Override
    public List<ShowResponse> getShowsByMovie(int movieId) {
        if (movieId <= 0) {
            throw new IllegalArgumentException("Invalid movie ID.");
        }
        return showDAO.getShowsByMovie(movieId);
    }

    @Override
    public List<ShowResponse> getShowsByTheatre(int theatreId) {
        if (theatreId <= 0) {
            throw new IllegalArgumentException("Invalid theatre ID.");
        }
        return showDAO.getShowsByTheatre(theatreId);
    }

    @Override
    public List<ShowResponse> getShowsByScreen(int screenId) {
        if (screenId <= 0) {
            throw new IllegalArgumentException("Invalid screen ID.");
        }
        return showDAO.getShowsByScreen(screenId);
    }

    @Override
    public boolean updateShow(ShowRequest request, int showId) {
        // Validate request
        if (request.getMovieId() <= 0 || request.getScreenId() <= 0 || request.getStartTime() == null ||
            request.getEndTime() == null || request.getPrice() <= 0 || showId <= 0) {
            throw new IllegalArgumentException("Movie ID, screen ID, start time, end time, price, and show ID are required.");
        }
        return showDAO.updateShow(request, showId);
    }

    @Override
    public boolean deleteShow(int showId) {
        if (showId <= 0) {
            throw new IllegalArgumentException("Invalid show ID.");
        }
        return showDAO.deleteShow(showId);
    }
}
