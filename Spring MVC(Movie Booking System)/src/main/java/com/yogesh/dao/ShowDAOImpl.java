package com.yogesh.dao;

import com.yogesh.dto.request.ShowRequest;
import com.yogesh.dto.response.ShowResponse;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class ShowDAOImpl implements ShowDAO {

    @Override
    public ShowResponse addShow(ShowRequest request) {
        String sql = "INSERT INTO shows (movie_id, screen_id, start_time, end_time, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, request.getMovieId());
            pstmt.setInt(2, request.getScreenId());
            pstmt.setTimestamp(3, request.getStartTime());
            pstmt.setTimestamp(4, request.getEndTime());
            pstmt.setDouble(5, request.getPrice());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return getShowById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ShowResponse getShowById(int showId) {
        String sql = "SELECT s.*, m.title AS movie_title, t.theatre_id, t.name AS theatre_name, sc.name AS screen_name " +
                     "FROM shows s " +
                     "JOIN movies m ON s.movie_id = m.movie_id " +
                     "JOIN screens sc ON s.screen_id = sc.screen_id " +
                     "JOIN theatres t ON sc.theatre_id = t.theatre_id " +
                     "WHERE s.show_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, showId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ShowResponse show = new ShowResponse();
                show.setShowId(rs.getInt("show_id"));
                show.setMovieId(rs.getInt("movie_id"));
                show.setMovieTitle(rs.getString("movie_title"));
                show.setTheatreId(rs.getInt("theatre_id"));  // Use theatre_id from theatres table
                show.setTheatreName(rs.getString("theatre_name"));
                show.setScreenId(rs.getInt("screen_id"));
                show.setScreenName(rs.getString("screen_name"));
                show.setStartTime(rs.getTimestamp("start_time"));
                show.setEndTime(rs.getTimestamp("end_time"));
                show.setPrice(rs.getDouble("price"));
                return show;
            } else {
                System.out.println("No show found for ID: " + showId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<ShowResponse> getShowsByMovie(int movieId) {
        List<ShowResponse> shows = new ArrayList<>();
        String sql = "SELECT * FROM shows WHERE movie_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                shows.add(mapShowToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shows;
    }

    @Override
    public List<ShowResponse> getShowsByTheatre(int theatreId) {
        List<ShowResponse> shows = new ArrayList<>();
        String sql = "SELECT s.* FROM shows s JOIN screens sc ON s.screen_id = sc.screen_id WHERE sc.theatre_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, theatreId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                shows.add(mapShowToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shows;
    }

    @Override
    public List<ShowResponse> getShowsByScreen(int screenId) {
        List<ShowResponse> shows = new ArrayList<>();
        String sql = "SELECT * FROM shows WHERE screen_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, screenId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                shows.add(mapShowToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shows;
    }

    @Override
    public boolean updateShow(ShowRequest request, int showId) {
        String sql = "UPDATE shows SET movie_id = ?, screen_id = ?, start_time = ?, end_time = ?, price = ? WHERE show_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, request.getMovieId());
            pstmt.setInt(2, request.getScreenId());
            pstmt.setTimestamp(3, request.getStartTime());
            pstmt.setTimestamp(4, request.getEndTime());
            pstmt.setDouble(5, request.getPrice());
            pstmt.setInt(6, showId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteShow(int showId) {
        String sql = "DELETE FROM shows WHERE show_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, showId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to ShowResponse
    private ShowResponse mapShowToResponse(ResultSet rs) throws SQLException {
        ShowResponse response = new ShowResponse();
        response.setShowId(rs.getInt("show_id"));
        response.setMovieId(rs.getInt("movie_id"));
        response.setScreenId(rs.getInt("screen_id"));
        response.setStartTime(rs.getTimestamp("start_time"));
        response.setEndTime(rs.getTimestamp("end_time"));
        response.setPrice(rs.getDouble("price"));
        return response;
    }
    
}

