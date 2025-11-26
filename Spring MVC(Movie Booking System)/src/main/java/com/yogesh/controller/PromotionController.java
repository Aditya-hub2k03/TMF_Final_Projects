package com.yogesh.controller;

import com.yogesh.dto.request.PromotionRequest;
import com.yogesh.dto.response.PromotionResponse;
import com.yogesh.dto.response.UserResponse;
import com.yogesh.service.PromotionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/promotion")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/list")
    public ModelAndView listPromotions(HttpSession session) {
        ModelAndView mav = new ModelAndView("admin/promotions");
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("APPLICATION_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            List<PromotionResponse> promotions = promotionService.getAllPromotions();
            mav.addObject("promotions", promotions);
        }
        return mav;
    }

    @GetMapping("/add")
    public String showAddPromotionForm(HttpSession session) {
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("APPLICATION_ADMIN")) {
            return "redirect:/user/login";
        }
        return "admin/add_promotion";
    }

    @PostMapping("/add")
    public ModelAndView addPromotion(@ModelAttribute PromotionRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        UserResponse user = (UserResponse) session.getAttribute("user");
        if (user == null || !user.getRole().name().equals("APPLICATION_ADMIN")) {
            mav.setViewName("redirect:/user/login");
        } else {
            try {
                promotionService.addPromotion(request);
                mav.setViewName("redirect:/promotion/list");
            } catch (Exception e) {
                mav.setViewName("admin/add_promotion");
                mav.addObject("error", e.getMessage());
            }
        }
        return mav;
    }
}
