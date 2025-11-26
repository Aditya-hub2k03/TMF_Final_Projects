package com.yogesh.controller;

import com.yogesh.dto.request.ShowRequest;
import com.yogesh.dto.response.ShowResponse;
import com.yogesh.dto.response.UserResponse;
import com.yogesh.service.ShowService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/show")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/manage/{theatreId}")
    public ModelAndView manageShows(@PathVariable int theatreId, HttpSession session) {
        ModelAndView mav = new ModelAndView("theatre/manage_shows");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("THEATRE_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            List<ShowResponse> shows = showService.getShowsByTheatre(theatreId);
            mav.addObject("shows", shows);
            mav.addObject("theatreId", theatreId);
        }
        return mav;
    }

    @GetMapping("/add/{theatreId}")
    public ModelAndView showAddShowForm(@PathVariable int theatreId, HttpSession session) {
        ModelAndView mav = new ModelAndView("theatre/add_show");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("THEATRE_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            mav.addObject("theatreId", theatreId);
        }
        return mav;
    }

    @PostMapping("/add/{theatreId}")
    public ModelAndView addShow(@PathVariable int theatreId, @ModelAttribute ShowRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("THEATRE_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            try {
                showService.addShow(request);
                mav.setViewName("redirect:/show/manage/" + theatreId);
            } catch (Exception e) {
                mav.setViewName("theatre/add_show");
                mav.addObject("error", e.getMessage());
                mav.addObject("theatreId", theatreId);
            }
        }
        return mav;
    }
}
