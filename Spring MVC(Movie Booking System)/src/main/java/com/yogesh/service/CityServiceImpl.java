package com.yogesh.service;

import com.yogesh.dao.CityDAO;
import com.yogesh.dto.request.CityRequest;
import com.yogesh.dto.response.CityResponse;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class CityServiceImpl implements CityService {

    private final CityDAO cityDAO;

    public CityServiceImpl(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Override
    public CityResponse addCity(CityRequest request) {
        // Validate request
        if (request.getName() == null) {
            throw new IllegalArgumentException("City name is required.");
        }
        return cityDAO.addCity(request);
    }

    @Override
    public CityResponse getCityById(int cityId) {
        if (cityId <= 0) {
            throw new IllegalArgumentException("Invalid city ID.");
        }
        return cityDAO.getCityById(cityId);
    }

    @Override
    public List<CityResponse> getAllCities() {
        return cityDAO.getAllCities();
    }

    @Override
    public boolean updateCity(CityRequest request, int cityId) {
        // Validate request
        if (request.getName() == null || cityId <= 0) {
            throw new IllegalArgumentException("City name and city ID are required.");
        }
        return cityDAO.updateCity(request, cityId);
    }

    @Override
    public boolean deleteCity(int cityId) {
        if (cityId <= 0) {
            throw new IllegalArgumentException("Invalid city ID.");
        }
        return cityDAO.deleteCity(cityId);
    }
}
