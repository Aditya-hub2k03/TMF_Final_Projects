package com.yogesh.dao;

import com.yogesh.dto.request.CityRequest;
import com.yogesh.dto.response.CityResponse;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class CityDAOImpl implements CityDAO {

    @Override
    public CityResponse addCity(CityRequest request) {
        String sql = "INSERT INTO cities (name) VALUES (?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, request.getName());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return getCityById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CityResponse getCityById(int cityId) {
        String sql = "SELECT * FROM cities WHERE city_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cityId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapCityToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CityResponse> getAllCities() {
        List<CityResponse> cities = new ArrayList<>();
        String sql = "SELECT * FROM cities";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cities.add(mapCityToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public boolean updateCity(CityRequest request, int cityId) {
        String sql = "UPDATE cities SET name = ? WHERE city_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getName());
            pstmt.setInt(2, cityId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCity(int cityId) {
        String sql = "DELETE FROM cities WHERE city_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cityId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to CityResponse
    private CityResponse mapCityToResponse(ResultSet rs) throws SQLException {
        CityResponse response = new CityResponse();
        response.setCityId(rs.getInt("city_id"));
        response.setName(rs.getString("name"));
        return response;
    }
}
