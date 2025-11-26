package com.yogesh.service;

import com.yogesh.dto.request.PromotionRequest;
import com.yogesh.dto.response.PromotionResponse;
import java.util.List;

public interface PromotionService {
    PromotionResponse addPromotion(PromotionRequest request);
    PromotionResponse getPromotionById(int promotionId);
    PromotionResponse getPromotionByCode(String code);
    List<PromotionResponse> getAllPromotions();
    boolean updatePromotion(PromotionRequest request, int promotionId);
    boolean deletePromotion(int promotionId);
}
