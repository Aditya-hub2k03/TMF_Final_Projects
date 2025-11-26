package com.yogesh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsResponse {
    private int bankId;
    private int userId;
    private String cardHolderName;
    private String expiryDate;
    private String lastFourDigits; // For security, only show last 4 digits
}
