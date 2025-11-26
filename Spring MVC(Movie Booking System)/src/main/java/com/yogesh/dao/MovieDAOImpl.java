package com.yogesh.dao;

import com.yogesh.dto.request.MovieRequest;
import com.yogesh.dto.response.MovieResponse;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class MovieDAOImpl implements MovieDAO {

    @Override
    public MovieResponse addMovie(MovieRequest request) {
        String sql = "INSERT INTO movies (title, description, duration_minutes, release_date, image_url, language) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, request.getTitle());
            pstmt.setString(2, request.getDescription());
            pstmt.setInt(3, request.getDurationMinutes());
            pstmt.setDate(4, request.getReleaseDate());
            pstmt.setString(5, request.getImageUrl());
            pstmt.setString(6, request.getLanguage());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return getMovieById(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MovieResponse getMovieById(int movieId) {
        String sql = "SELECT * FROM movies WHERE movie_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapMovieToResponse(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        List<MovieResponse> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                movies.add(mapMovieToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public List<MovieResponse> getMoviesByLanguage(String language) {
        List<MovieResponse> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE language = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, language);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                movies.add(mapMovieToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public boolean updateMovie(MovieRequest request, int movieId) {
        String sql = "UPDATE movies SET title = ?, description = ?, duration_minutes = ?, release_date = ?, image_url = ?, language = ? WHERE movie_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getTitle());
            pstmt.setString(2, request.getDescription());
            pstmt.setInt(3, request.getDurationMinutes());
            pstmt.setDate(4, request.getReleaseDate());
            pstmt.setString(5, request.getImageUrl());
            pstmt.setString(6, request.getLanguage());
            pstmt.setInt(7, movieId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMovie(int movieId) {
        String sql = "DELETE FROM movies WHERE movie_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map ResultSet to MovieResponse
    private MovieResponse mapMovieToResponse(ResultSet rs) throws SQLException {
        MovieResponse response = new MovieResponse();
        response.setMovieId(rs.getInt("movie_id"));
        response.setTitle(rs.getString("title"));
        response.setDescription(rs.getString("description"));
        response.setDurationMinutes(rs.getInt("duration_minutes"));
        response.setReleaseDate(rs.getDate("release_date"));
        response.setImageUrl(rs.getString("image_url"));
        response.setLanguage(rs.getString("language"));
        return response;
    }
    @Override
    public List<MovieResponse> searchMovies(String query) {
        List<MovieResponse> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE title LIKE ? OR description LIKE ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchQuery = "%" + query + "%";
            pstmt.setString(1, searchQuery);
            pstmt.setString(2, searchQuery);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                movies.add(mapMovieToResponse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

}
