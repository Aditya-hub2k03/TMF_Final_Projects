package com.yogesh.controller;

import com.yogesh.dto.request.ScreenRequest;
import com.yogesh.dto.response.ScreenResponse;
import com.yogesh.dto.response.UserResponse;
import com.yogesh.service.ScreenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/screen")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @GetMapping("/manage/{theatreId}")
    public ModelAndView manageScreens(@PathVariable int theatreId, HttpSession session) {
        ModelAndView mav = new ModelAndView("theatre/manage_screens");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("THEATRE_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            List<ScreenResponse> screens = screenService.getScreensByTheatre(theatreId);
            mav.addObject("screens", screens);
            mav.addObject("theatreId", theatreId);
        }
        return mav;
    }

    @GetMapping("/add/{theatreId}")
    public ModelAndView showAddScreenForm(@PathVariable int theatreId, HttpSession session) {
        ModelAndView mav = new ModelAndView("theatre/add_screen");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("THEATRE_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            mav.addObject("theatreId", theatreId);
        }
        return mav;
    }

    @PostMapping("/add/{theatreId}")
    public ModelAndView addScreen(@PathVariable int theatreId, @ModelAttribute ScreenRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("THEATRE_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            request.setTheatreId(theatreId);
            try {
                screenService.addScreen(request);
                mav.setViewName("redirect:/screen/manage/" + theatreId);
            } catch (Exception e) {
                mav.setViewName("theatre/add_screen");
                mav.addObject("error", e.getMessage());
                mav.addObject("theatreId", theatreId);
            }
        }
        return mav;
    }
}
