package com.yogesh.service;

import com.yogesh.dao.TheatreDAO;
import com.yogesh.dto.request.TheatreRequest;
import com.yogesh.dto.response.TheatreResponse;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class TheatreServiceImpl implements TheatreService {

    private final TheatreDAO theatreDAO;

    public TheatreServiceImpl(TheatreDAO theatreDAO) {
        this.theatreDAO = theatreDAO;
    }

    @Override
    public TheatreResponse addTheatre(TheatreRequest request) {
        // Validate request
        if (request.getName() == null || request.getAddress() == null || request.getCity() == null) {
            throw new IllegalArgumentException("Name, address, and city are required.");
        }
        return theatreDAO.addTheatre(request);
    }

    @Override
    public TheatreResponse getTheatreById(int theatreId) {
        if (theatreId <= 0) {
            throw new IllegalArgumentException("Invalid theatre ID.");
        }
        return theatreDAO.getTheatreById(theatreId);
    }

    @Override
    public List<TheatreResponse> getAllTheatres() {
        return theatreDAO.getAllTheatres();
    }

    @Override
    public List<TheatreResponse> getTheatresByCity(String city) {
        if (city == null) {
            throw new IllegalArgumentException("City is required.");
        }
        return theatreDAO.getTheatresByCity(city);
    }

    @Override
    public boolean updateTheatre(TheatreRequest request, int theatreId) {
        // Validate request
        if (request.getName() == null || request.getAddress() == null || request.getCity() == null || theatreId <= 0) {
            throw new IllegalArgumentException("Name, address, city, and theatre ID are required.");
        }
        return theatreDAO.updateTheatre(request, theatreId);
    }

    @Override
    public boolean deleteTheatre(int theatreId) {
        if (theatreId <= 0) {
            throw new IllegalArgumentException("Invalid theatre ID.");
        }
        return theatreDAO.deleteTheatre(theatreId);
    }
    @Override
    public List<TheatreResponse> getTheatresByAdmin(int adminId) {
        return theatreDAO.getTheatresByAdmin(adminId);
    }

}
