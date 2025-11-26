package com.yogesh.service;

import com.yogesh.dao.ScreenDAO;
import com.yogesh.dto.request.ScreenRequest;
import com.yogesh.dto.response.ScreenResponse;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class ScreenServiceImpl implements ScreenService {

    private final ScreenDAO screenDAO;

    public ScreenServiceImpl(ScreenDAO screenDAO) {
        this.screenDAO = screenDAO;
    }

    @Override
    public ScreenResponse addScreen(ScreenRequest request) {
        // Validate request
        if (request.getTheatreId() <= 0 || request.getName() == null || request.getTotalSeats() <= 0) {
            throw new IllegalArgumentException("Theatre ID, name, and total seats are required.");
        }
        return screenDAO.addScreen(request);
    }

    @Override
    public ScreenResponse getScreenById(int screenId) {
        if (screenId <= 0) {
            throw new IllegalArgumentException("Invalid screen ID.");
        }
        return screenDAO.getScreenById(screenId);
    }

    @Override
    public List<ScreenResponse> getScreensByTheatre(int theatreId) {
        if (theatreId <= 0) {
            throw new IllegalArgumentException("Invalid theatre ID.");
        }
        return screenDAO.getScreensByTheatre(theatreId);
    }

    @Override
    public boolean updateScreen(ScreenRequest request, int screenId) {
        // Validate request
        if (request.getTheatreId() <= 0 || request.getName() == null || request.getTotalSeats() <= 0 || screenId <= 0) {
            throw new IllegalArgumentException("Theatre ID, name, total seats, and screen ID are required.");
        }
        return screenDAO.updateScreen(request, screenId);
    }

    @Override
    public boolean deleteScreen(int screenId) {
        if (screenId <= 0) {
            throw new IllegalArgumentException("Invalid screen ID.");
        }
        return screenDAO.deleteScreen(screenId);
    }
}
