package com.yogesh.service;

import com.yogesh.dao.PromotionDAO;
import com.yogesh.dto.request.PromotionRequest;
import com.yogesh.dto.response.PromotionResponse;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionDAO promotionDAO;

    public PromotionServiceImpl(PromotionDAO promotionDAO) {
        this.promotionDAO = promotionDAO;
    }

    @Override
    public PromotionResponse addPromotion(PromotionRequest request) {
        // Validate request
        if (request.getCode() == null || request.getDiscountPercentage() <= 0 || request.getStartDate() == null || request.getEndDate() == null) {
            throw new IllegalArgumentException("Promotion code, discount percentage, start date, and end date are required.");
        }
        return promotionDAO.addPromotion(request);
    }

    @Override
    public PromotionResponse getPromotionById(int promotionId) {
        if (promotionId <= 0) {
            throw new IllegalArgumentException("Invalid promotion ID.");
        }
        return promotionDAO.getPromotionById(promotionId);
    }

    @Override
    public PromotionResponse getPromotionByCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Promotion code is required.");
        }
        return promotionDAO.getPromotionByCode(code);
    }

    @Override
    public List<PromotionResponse> getAllPromotions() {
        return promotionDAO.getAllPromotions();
    }

    @Override
    public boolean updatePromotion(PromotionRequest request, int promotionId) {
        // Validate request
        if (request.getCode() == null || request.getDiscountPercentage() <= 0 || request.getStartDate() == null ||
            request.getEndDate() == null || promotionId <= 0) {
            throw new IllegalArgumentException("Promotion code, discount percentage, start date, end date, and promotion ID are required.");
        }
        return promotionDAO.updatePromotion(request, promotionId);
    }

    @Override
    public boolean deletePromotion(int promotionId) {
        if (promotionId <= 0) {
            throw new IllegalArgumentException("Invalid promotion ID.");
        }
        return promotionDAO.deletePromotion(promotionId);
    }
}
