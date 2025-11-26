package com.yogesh.controller;

import com.yogesh.dto.response.UserResponse;
import com.yogesh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(HttpSession session) {
        ModelAndView mav = new ModelAndView("admin/dashboard");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("APPLICATION_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            List<UserResponse> users = userService.getAllUsers();
            mav.addObject("users", users);
        }
        return mav;
    }

    @PostMapping("/update-role/{userId}")
    public ModelAndView updateUserRole(@PathVariable int userId, @RequestParam String role, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("APPLICATION_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            try {
                userService.updateUserRole(userId, role);
                mav.setViewName("redirect:/admin/dashboard");
            } catch (Exception e) {
                mav.setViewName("redirect:/admin/dashboard?error=" + e.getMessage());
            }
        }
        return mav;
    }
}
