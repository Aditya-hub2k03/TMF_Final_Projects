package com.yogesh.service;

import com.yogesh.dto.request.CityRequest;
import com.yogesh.dto.response.CityResponse;
import java.util.List;

public interface CityService {
    CityResponse addCity(CityRequest request);
    CityResponse getCityById(int cityId);
    List<CityResponse> getAllCities();
    boolean updateCity(CityRequest request, int cityId);
    boolean deleteCity(int cityId);
}
