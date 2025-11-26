package com.yogesh.controller;

import com.yogesh.dto.request.UserLoginRequest;
import com.yogesh.dto.request.UserRegistrationRequest;
import com.yogesh.dto.request.UpdatePasswordRequest;
import com.yogesh.dto.response.UserResponse;
import com.yogesh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "user/register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute UserRegistrationRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            UserResponse user = userService.registerUser(request);
            mav.setViewName("redirect:/user/login");
        } catch (Exception e) {
            mav.setViewName("user/register");
            mav.addObject("error", e.getMessage());
        }
        return mav;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@ModelAttribute UserLoginRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            UserResponse user = userService.loginUser(request);
            session.setAttribute("user", user);
            mav.setViewName("redirect:/");
        } catch (Exception e) {
            mav.setViewName("user/login");
            mav.addObject("error", e.getMessage());
        }
        return mav;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public ModelAndView showProfile(HttpSession session) {
        ModelAndView mav = new ModelAndView("user/profile");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            mav.setViewName("redirect:/user/login");
        } else {
            mav.addObject("user", user);
        }
        return mav;
    }

    @PostMapping("/update-password")
    public ModelAndView updatePassword(@ModelAttribute UpdatePasswordRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null) {
            mav.setViewName("redirect:/user/login");
        } else {
            request.setUserId(user.getUserId());
            try {
                userService.updatePassword(request);
                mav.setViewName("redirect:/user/profile?success=true");
            } catch (Exception e) {
                mav.setViewName("redirect:/user/profile?error=" + e.getMessage());
            }
        }
        return mav;
    }
    @PostMapping("/update-role/{userId}")
    public ModelAndView updateUserRole(@PathVariable int userId, @RequestParam String role, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse currentUser = (UserResponse) session.getAttribute("user");

        if (currentUser == null || !currentUser.getRole().name().equals("APPLICATION_ADMIN")) {
            mav.setViewName("redirect:/user/login");
            return mav;
        }

        try {
            userService.updateUserRole(userId, role);

            // If the updated user is the current user, update the session
            if (currentUser.getUserId() == userId) {
                UserResponse updatedUser = userService.getUserById(userId);
                session.setAttribute("user", updatedUser);
            }

            mav.setViewName("redirect:/admin/dashboard");
        } catch (Exception e) {
            mav.setViewName("redirect:/admin/dashboard?error=" + e.getMessage());
        }
        return mav;
    }

}
