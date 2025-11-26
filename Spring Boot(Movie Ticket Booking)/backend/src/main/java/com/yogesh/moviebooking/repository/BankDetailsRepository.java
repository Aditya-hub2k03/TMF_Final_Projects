package com.yogesh.moviebooking.repository;

import com.yogesh.moviebooking.model.BankDetails;
import com.yogesh.moviebooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {
    List<BankDetails> findByUser(User user);
    Optional<BankDetails> findFirstByUser(User user);
}