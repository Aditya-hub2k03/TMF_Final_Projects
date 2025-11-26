package com.yogesh.moviebooking.controller;

import com.yogesh.moviebooking.model.BankDetails;
import com.yogesh.moviebooking.model.User;
import com.yogesh.moviebooking.repository.BankDetailsRepository;
import com.yogesh.moviebooking.repository.UserRepository;
import com.yogesh.moviebooking.security.AppUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    private final UserRepository userRepository;
    private final BankDetailsRepository bankDetailsRepository;

    public UserController(UserRepository userRepository,
                          BankDetailsRepository bankDetailsRepository) {
        this.userRepository = userRepository;
        this.bankDetailsRepository = bankDetailsRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(@AuthenticationPrincipal AppUserDetails principal) {
        return ResponseEntity.ok(principal.getDomainUser());
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateProfile(@AuthenticationPrincipal AppUserDetails principal,
                                              @RequestBody Map<String, String> body) {
        User user = principal.getDomainUser();
        if (body.containsKey("username")) {
            user.setUsername(body.get("username"));
        }
        if (body.containsKey("email")) {
            user.setEmail(body.get("email"));
        }
        return ResponseEntity.ok(userRepository.save(user));
    }

    // --- Bank details ---

    @GetMapping("/me/bank-details")
    public List<BankDetails> myBankDetails(@AuthenticationPrincipal AppUserDetails principal) {
        return bankDetailsRepository.findByUser(principal.getDomainUser());
    }

    @PostMapping("/me/bank-details")
    public BankDetails addBankDetails(@AuthenticationPrincipal AppUserDetails principal,
                                      @RequestBody BankDetails body) {
        body.setId(null);
        body.setUser(principal.getDomainUser());
        return bankDetailsRepository.save(body);
    }

    @DeleteMapping("/me/bank-details/{id}")
    public ResponseEntity<?> deleteBankDetails(@AuthenticationPrincipal AppUserDetails principal,
                                               @PathVariable Long id) {
        return bankDetailsRepository.findById(id).map(bd -> {
            if (!bd.getUser().getId().equals(principal.getDomainUser().getId())) {
                return ResponseEntity.status(403).build();
            }
            bankDetailsRepository.delete(bd);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}