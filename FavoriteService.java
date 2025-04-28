package com.homehunter.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> getUserFavorites(User user) {
        return favoriteRepository.findByUser(user);
    }

    public Favorite addFavorite(User user, Listing listing) {
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setListing(listing);
        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(User user, Listing listing) {
        favoriteRepository.deleteByUserAndListing(user, listing);
    }
}