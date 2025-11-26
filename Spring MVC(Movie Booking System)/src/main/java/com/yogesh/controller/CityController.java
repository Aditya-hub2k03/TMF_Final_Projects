package com.yogesh.controller;

import com.yogesh.dto.request.CityRequest;
import com.yogesh.dto.response.CityResponse;
import com.yogesh.dto.response.UserResponse;
import com.yogesh.service.CityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/list")
    public ModelAndView listCities(HttpSession session) {
        ModelAndView mav = new ModelAndView("admin/cities");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("APPLICATION_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            List<CityResponse> cities = cityService.getAllCities();
            mav.addObject("cities", cities);
        }
        return mav;
    }

    @GetMapping("/add")
    public String showAddCityForm(HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("APPLICATION_ADMIN")) {
            return "redirect:/user/login";
        }
        return "admin/add_city";
    }

    @PostMapping("/add")
    public ModelAndView addCity(@ModelAttribute CityRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("APPLICATION_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            try {
                cityService.addCity(request);
                mav.setViewName("redirect:/city/list");
            } catch (Exception e) {
                mav.setViewName("admin/add_city");
                mav.addObject("error", e.getMessage());
            }
        }
        return mav;
    }
}
