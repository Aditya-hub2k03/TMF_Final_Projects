package com.yogesh.controller;

import com.yogesh.dto.request.TheatreRequest;
import com.yogesh.dto.response.TheatreResponse;
import com.yogesh.dto.response.UserResponse;
import com.yogesh.service.TheatreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/theatre")
public class TheatreController {

    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(HttpSession session) {
        ModelAndView mav = new ModelAndView("theatre/dashboard");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("THEATRE_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            List<TheatreResponse> theatres = theatreService.getTheatresByAdmin(user.getUserId());
            mav.addObject("theatres", theatres);
        }
        return mav;
    }


    @GetMapping("/add")
    public String showAddTheatreForm(HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("THEATRE_ADMIN")) {
            return "redirect:/user/login";
        }
        return "theatre/add";
    }

    @PostMapping("/add")
    public ModelAndView addTheatre(@ModelAttribute TheatreRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("THEATRE_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            request.setAdminId(user.getUserId());
            try {
                theatreService.addTheatre(request);
                mav.setViewName("redirect:/theatre/dashboard");
            } catch (Exception e) {
                mav.setViewName("theatre/add");
                mav.addObject("error", e.getMessage());
            }
        }
        return mav;
    }

    @GetMapping("/edit/{theatreId}")
    public ModelAndView showEditTheatreForm(@PathVariable int theatreId, HttpSession session) {
        ModelAndView mav = new ModelAndView("theatre/edit");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("THEATRE_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            TheatreResponse theatre = theatreService.getTheatreById(theatreId);
            if (theatre != null && theatre.getAdminId() == user.getUserId()) {
                mav.addObject("theatre", theatre);
            } else {
                mav.setViewName("redirect:/theatre/dashboard");
            }
        }
        return mav;
    }

    @PostMapping("/edit/{theatreId}")
    public ModelAndView editTheatre(@PathVariable int theatreId, @ModelAttribute TheatreRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("THEATRE_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            try {
                theatreService.updateTheatre(request, theatreId);
                mav.setViewName("redirect:/theatre/dashboard");
            } catch (Exception e) {
                mav.setViewName("redirect:/theatre/edit/" + theatreId + "?error=" + e.getMessage());
            }
        }
        return mav;
    }
}
