package com.yogesh.moviebooking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "theatres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theatre_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin; // Theatre admin user

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Override
    public String toString() {
        return name + " (" + (city != null ? city.getName() : "No city") + ")";
    }
}