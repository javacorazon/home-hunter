package com.homehunter.controller;

import com.homehunter.model.Listing;
import com.homehunter.model.User;
import com.homehunter.service.ListingService;
import com.homehunter.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final ListingService listingService;
    private final ListingService listeningService;

    public AdminController(UserService userService, ListingService listingService, ListingService listingService1) {
        this.userService = userService;
        this.listingService = listingService1;
        this.listeningService = listingService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error fetching users",
                    e
            );
        }
    }

    @GetMapping("/listings/pending")
    public ResponseEntity<List<Listing>> getPendingListings() {
        try {
            List<Listing> pendingListings = listingService.getPendingListings();
            return ResponseEntity.ok(pendingListings);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error fetching pending listings",
                    e
            );
        }
    }

    @PutMapping("/listings/{id}/approve")
    public ResponseEntity<Listing> approveListing(@PathVariable Long id) {
        try {
            Listing approvedListing = listingService.approveListing(id);
            return ResponseEntity.ok(approvedListing);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Listing not found with id: " + id,
                    e
            );
        }
    }

    @DeleteMapping("/listings/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        try {
            listingService.deleteListing(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Listing not found with id: " + id,
                    e
            );
        }
    }
}