package com.yogesh.dao;

import com.yogesh.dto.request.MovieRequest;
import com.yogesh.dto.response.MovieResponse;
import java.util.List;

public interface MovieDAO {
    MovieResponse addMovie(MovieRequest request);
    MovieResponse getMovieById(int movieId);
    List<MovieResponse> getAllMovies();
    List<MovieResponse> getMoviesByLanguage(String language);
    boolean updateMovie(MovieRequest request, int movieId);
    boolean deleteMovie(int movieId);
    List<MovieResponse> searchMovies(String query);

}
