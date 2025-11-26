package com.yogesh.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yogesh.dto.response.UserResponse;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("user") != null) {
            mav.setViewName("logged_in_home");
            mav.addObject("username", ((UserResponse) session.getAttribute("user")).getUsername());
        } else {
            mav.setViewName("home");
        }
        return mav;
    }
}
