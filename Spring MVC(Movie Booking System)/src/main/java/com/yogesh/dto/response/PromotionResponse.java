package com.yogesh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponse {
    private int promotionId;
    private String code;
    private double discountPercentage;
    private Date startDate;
    private Date endDate;
    private boolean isActive;
}
