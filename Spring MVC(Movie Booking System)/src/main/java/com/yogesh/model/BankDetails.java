package com.yogesh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
    private int bankId;
    private int userId; // References User.userId
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
}
