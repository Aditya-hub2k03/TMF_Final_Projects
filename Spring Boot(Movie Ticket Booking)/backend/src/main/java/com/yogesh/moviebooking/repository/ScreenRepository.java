package com.yogesh.moviebooking.repository;

import com.yogesh.moviebooking.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
}