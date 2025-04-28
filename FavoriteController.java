package com.homehunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public ResponseEntity<List<Favorite>> getUserFavorites(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(favoriteService.getUserFavorites(user));
    }

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(
            @AuthenticationPrincipal User user,
            @RequestBody Listing listing) {
        return ResponseEntity.ok(favoriteService.addFavorite(user, listing));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(
            @AuthenticationPrincipal User user,
            @RequestBody Listing listing) {
        favoriteService.removeFavorite(user, listing);
        return ResponseEntity.noContent().build();
    }
}