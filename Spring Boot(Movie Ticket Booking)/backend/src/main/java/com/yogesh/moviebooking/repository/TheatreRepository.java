package com.yogesh.moviebooking.repository;

import com.yogesh.moviebooking.model.Theatre;
import com.yogesh.moviebooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    List<Theatre> findByAdmin(User admin);
}