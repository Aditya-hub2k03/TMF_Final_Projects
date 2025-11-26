package com.yogesh.controller;

import com.yogesh.dto.request.ReviewRequest;
import com.yogesh.dto.response.ReviewResponse;
import com.yogesh.dto.response.UserResponse;
import com.yogesh.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/movie/{movieId}")
    public ModelAndView getReviewsByMovie(@PathVariable int movieId) {
        ModelAndView mav = new ModelAndView("review/list");
        List<ReviewResponse> reviews = reviewService.getReviewsByMovie(movieId);
        mav.addObject("reviews", reviews);
        mav.addObject("movieId", movieId);
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView addReview(@ModelAttribute ReviewRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            mav.setViewName("redirect:/user/login");
        } else {
            request.setUserId(user.getUserId());
            try {
                reviewService.addReview(request);
                mav.setViewName("redirect:/review/movie/" + request.getMovieId());
            } catch (Exception e) {
                mav.setViewName("redirect:/review/movie/" + request.getMovieId() + "?error=" + e.getMessage());
            }
        }
        return mav;
    }
}
