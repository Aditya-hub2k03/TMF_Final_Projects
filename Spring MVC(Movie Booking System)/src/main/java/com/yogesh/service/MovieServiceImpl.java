package com.yogesh.service;

import com.yogesh.dao.MovieDAO;
import com.yogesh.dto.request.MovieRequest;
import com.yogesh.dto.response.MovieResponse;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieDAO movieDAO;

    public MovieServiceImpl(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public MovieResponse addMovie(MovieRequest request) {
        // Validate request
        if (request.getTitle() == null || request.getDescription() == null || request.getDurationMinutes() <= 0 ||
            request.getReleaseDate() == null || request.getLanguage() == null) {
            throw new IllegalArgumentException("Title, description, duration, release date, and language are required.");
        }
        return movieDAO.addMovie(request);
    }

    @Override
    public MovieResponse getMovieById(int movieId) {
        if (movieId <= 0) {
            throw new IllegalArgumentException("Invalid movie ID.");
        }
        return movieDAO.getMovieById(movieId);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        return movieDAO.getAllMovies();
    }

    @Override
    public List<MovieResponse> getMoviesByLanguage(String language) {
        if (language == null) {
            throw new IllegalArgumentException("Language is required.");
        }
        return movieDAO.getMoviesByLanguage(language);
    }

    @Override
    public boolean updateMovie(MovieRequest request, int movieId) {
        // Validate request
        if (request.getTitle() == null || request.getDescription() == null || request.getDurationMinutes() <= 0 ||
            request.getReleaseDate() == null || request.getLanguage() == null || movieId <= 0) {
            throw new IllegalArgumentException("Title, description, duration, release date, language, and movie ID are required.");
        }
        return movieDAO.updateMovie(request, movieId);
    }

    @Override
    public boolean deleteMovie(int movieId) {
        if (movieId <= 0) {
            throw new IllegalArgumentException("Invalid movie ID.");
        }
        return movieDAO.deleteMovie(movieId);
    }
    @Override
    public List<MovieResponse> searchMovies(String query) {
        return movieDAO.searchMovies(query);
    }

}
