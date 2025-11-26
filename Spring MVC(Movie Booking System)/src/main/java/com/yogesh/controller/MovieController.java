package com.yogesh.controller;

import com.yogesh.dto.request.MovieRequest;
import com.yogesh.dto.response.MovieResponse;
import com.yogesh.dto.response.UserResponse;
import com.yogesh.service.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/list")
    public ModelAndView listMovies() {
        ModelAndView mav = new ModelAndView("movie/list");
        List<MovieResponse> movies = movieService.getAllMovies();
        mav.addObject("movies", movies);
        return mav;
    }

    @GetMapping("/add")
    public String showAddMovieForm(HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("APPLICATION_ADMIN")) {
            return "redirect:/user/login";
        }
        return "movie/add";
    }

    @PostMapping("/add")
    public ModelAndView addMovie(@ModelAttribute MovieRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("APPLICATION_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            try {
                movieService.addMovie(request);
                mav.setViewName("redirect:/movie/list");
            } catch (Exception e) {
                mav.setViewName("movie/add");
                mav.addObject("error", e.getMessage());
            }
        }
        return mav;
    }
    @GetMapping("/search")
    public ModelAndView searchMovies(@RequestParam String query) {
        ModelAndView mav = new ModelAndView("movie/list");
        List<MovieResponse> movies = movieService.searchMovies(query);
        mav.addObject("movies", movies);
        return mav;
    }


}
