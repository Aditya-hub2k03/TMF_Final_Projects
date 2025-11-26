package com.yogesh.dao;

import com.yogesh.dto.request.SeatRequest;
import com.yogesh.dto.response.SeatResponse;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class SeatDAOImpl implements SeatDAO {

    @Override
    public SeatResponse addSeat(SeatRequest request) {
        String sql = "INSERT INTO seats (screen_id, seat_number) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, request.getScreenId());
            pstmt.setString(2, request.getSeatNumber());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return getSeatById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SeatResponse getSeatById(int seatId) {
        String sql = "SELECT * FROM seats WHERE seat_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, seatId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapSeatToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SeatResponse> getSeatsByScreen(int screenId) {
        List<SeatResponse> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE screen_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, screenId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                SeatResponse seat = new SeatResponse();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setScreenId(rs.getInt("screen_id"));
                seat.setSeatNumber(rs.getString("seat_number"));
                seat.setBooked(rs.getBoolean("is_booked"));
                seats.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }


    @Override
    public boolean updateSeatStatus(int seatId, boolean isBooked) {
        String sql = "UPDATE seats SET is_booked = ? WHERE seat_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isBooked);
            pstmt.setInt(2, seatId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSeat(int seatId) {
        String sql = "DELETE FROM seats WHERE seat_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, seatId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to SeatResponse
    private SeatResponse mapSeatToResponse(ResultSet rs) throws SQLException {
        SeatResponse response = new SeatResponse();
        response.setSeatId(rs.getInt("seat_id"));
        response.setScreenId(rs.getInt("screen_id"));
        response.setSeatNumber(rs.getString("seat_number"));
        response.setBooked(rs.getBoolean("is_booked"));
        return response;
    }
}
