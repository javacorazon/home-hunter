package com.homehunter.controller;

import com.homehunter.dto.GeolocationDTO;
import com.homehunter.dto.ListingDTO;
import com.homehunter.service.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maps")
public class MapsController {
    private final ListingService listingService;

    public MapsController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping("/nearby")
    public ResponseEntity<List<ListingDTO>> getNearbyListings(
            @AuthenticationPrincipal Long userId,
            @RequestBody GeolocationDTO geolocation) {
        // In a real implementation, you would calculate distance from user's location
        // For simplicity, we'll just return all listings
        List<ListingDTO> listings = listingService.getAllListings(userId);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/{listingId}/directions")
    public ResponseEntity<String> getDirections(
            @PathVariable Long listingId,
            @RequestParam double userLat,
            @RequestParam double userLng) {
        // In a real implementation, you would use Google Maps API
        // For now, return a mock response with WhatsApp link
        ListingDTO listing = listingService.getListingById(listingId, null);
        String whatsappLink = String.format("https://wa.me/%s?text=I'm interested in your property: %s",
                listing.getOwnerContact().replaceAll("[^0-9]", ""),
                listing.getTitle());

        return ResponseEntity.ok(whatsappLink);
    }
}