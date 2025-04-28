package com.homehunter.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private double price;
    private int bedrooms;
    private int bathrooms;
    private String category; // "APARTMENT", "HOUSE", "SHOP", etc.
    private String status; // "PENDING", "APPROVED", "REJECTED"

    @ManyToOne
    private User owner;

    @ElementCollection
    private Set<String> amenities = new HashSet<>();

    // Getters and Setters
    // Constructors
}