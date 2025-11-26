package com.yogesh.service;

import com.yogesh.dao.BankDetailsDAO;
import com.yogesh.dto.request.BankDetailsRequest;
import com.yogesh.dto.response.BankDetailsResponse;
import org.springframework.stereotype.Service;
@Service
public class BankDetailsServiceImpl implements BankDetailsService {

    private final BankDetailsDAO bankDetailsDAO;

    public BankDetailsServiceImpl(BankDetailsDAO bankDetailsDAO) {
        this.bankDetailsDAO = bankDetailsDAO;
    }

    @Override
    public BankDetailsResponse addBankDetails(BankDetailsRequest request) {
        // Validate request
        if (request.getUserId() <= 0 || request.getCardNumber() == null || request.getCardHolderName() == null ||
            request.getExpiryDate() == null || request.getCvv() == null) {
            throw new IllegalArgumentException("User ID, card number, card holder name, expiry date, and CVV are required.");
        }
        return bankDetailsDAO.addBankDetails(request);
    }

    @Override
    public BankDetailsResponse getBankDetailsById(int bankId) {
        if (bankId <= 0) {
            throw new IllegalArgumentException("Invalid bank details ID.");
        }
        return bankDetailsDAO.getBankDetailsById(bankId);
    }

    @Override
    public BankDetailsResponse getBankDetailsByUser(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        return bankDetailsDAO.getBankDetailsByUser(userId);
    }

    @Override
    public boolean updateBankDetails(BankDetailsRequest request, int bankId) {
        // Validate request
        if (request.getCardNumber() == null || request.getCardHolderName() == null ||
            request.getExpiryDate() == null || request.getCvv() == null || bankId <= 0) {
            throw new IllegalArgumentException("Card number, card holder name, expiry date, CVV, and bank details ID are required.");
        }
        return bankDetailsDAO.updateBankDetails(request, bankId);
    }

    @Override
    public boolean deleteBankDetails(int bankId) {
        if (bankId <= 0) {
            throw new IllegalArgumentException("Invalid bank details ID.");
        }
        return bankDetailsDAO.deleteBankDetails(bankId);
    }
}
