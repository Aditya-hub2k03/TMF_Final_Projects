package com.yogesh.dao;

import com.yogesh.dto.request.TheatreRequest;
import com.yogesh.dto.response.TheatreResponse;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class TheatreDAOImpl implements TheatreDAO {

    @Override
    public TheatreResponse addTheatre(TheatreRequest request) {
        String sql = "INSERT INTO theatres (name, address, city, admin_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, request.getName());
            pstmt.setString(2, request.getAddress());
            pstmt.setString(3, request.getCity());
            pstmt.setInt(4, request.getAdminId());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return getTheatreById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TheatreResponse getTheatreById(int theatreId) {
        String sql = "SELECT * FROM theatres WHERE theatre_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, theatreId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapTheatreToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TheatreResponse> getAllTheatres() {
        List<TheatreResponse> theatres = new ArrayList<>();
        String sql = "SELECT * FROM theatres";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                theatres.add(mapTheatreToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theatres;
    }

    @Override
    public List<TheatreResponse> getTheatresByCity(String city) {
        List<TheatreResponse> theatres = new ArrayList<>();
        String sql = "SELECT * FROM theatres WHERE city = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                theatres.add(mapTheatreToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theatres;
    }

    @Override
    public boolean updateTheatre(TheatreRequest request, int theatreId) {
        String sql = "UPDATE theatres SET name = ?, address = ?, city = ?, admin_id = ? WHERE theatre_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getName());
            pstmt.setString(2, request.getAddress());
            pstmt.setString(3, request.getCity());
            pstmt.setInt(4, request.getAdminId());
            pstmt.setInt(5, theatreId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteTheatre(int theatreId) {
        String sql = "DELETE FROM theatres WHERE theatre_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, theatreId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to TheatreResponse
    private TheatreResponse mapTheatreToResponse(ResultSet rs) throws SQLException {
        TheatreResponse response = new TheatreResponse();
        response.setTheatreId(rs.getInt("theatre_id"));
        response.setName(rs.getString("name"));
        response.setAddress(rs.getString("address"));
        response.setCity(rs.getString("city"));
        response.setAdminId(rs.getInt("admin_id"));
        return response;
    }
    @Override
    public List<TheatreResponse> getTheatresByAdmin(int adminId) {
        List<TheatreResponse> theatres = new ArrayList<>();
        String sql = "SELECT * FROM theatres WHERE admin_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, adminId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                theatres.add(mapTheatreToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theatres;
    }

}
