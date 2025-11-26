package com.yogesh.dao;

import com.yogesh.dto.request.ScreenRequest;
import com.yogesh.dto.response.ScreenResponse;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class ScreenDAOImpl implements ScreenDAO {

    @Override
    public ScreenResponse addScreen(ScreenRequest request) {
        String sql = "INSERT INTO screens (theatre_id, name, total_seats) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, request.getTheatreId());
            pstmt.setString(2, request.getName());
            pstmt.setInt(3, request.getTotalSeats());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return getScreenById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ScreenResponse getScreenById(int screenId) {
        String sql = "SELECT * FROM screens WHERE screen_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, screenId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapScreenToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ScreenResponse> getScreensByTheatre(int theatreId) {
        List<ScreenResponse> screens = new ArrayList<>();
        String sql = "SELECT * FROM screens WHERE theatre_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, theatreId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                screens.add(mapScreenToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screens;
    }

    @Override
    public boolean updateScreen(ScreenRequest request, int screenId) {
        String sql = "UPDATE screens SET theatre_id = ?, name = ?, total_seats = ? WHERE screen_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, request.getTheatreId());
            pstmt.setString(2, request.getName());
            pstmt.setInt(3, request.getTotalSeats());
            pstmt.setInt(4, screenId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteScreen(int screenId) {
        String sql = "DELETE FROM screens WHERE screen_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, screenId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to ScreenResponse
    private ScreenResponse mapScreenToResponse(ResultSet rs) throws SQLException {
        ScreenResponse response = new ScreenResponse();
        response.setScreenId(rs.getInt("screen_id"));
        response.setTheatreId(rs.getInt("theatre_id"));
        response.setName(rs.getString("name"));
        response.setTotalSeats(rs.getInt("total_seats"));
        return response;
    }
}
