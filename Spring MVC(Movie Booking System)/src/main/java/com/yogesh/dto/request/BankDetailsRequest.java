package com.yogesh.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsRequest {
    private int userId;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
}
