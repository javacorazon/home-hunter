package com.homehunter.service;

import com.homehunter.model.Listing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public Listing createListing(Listing listing, User owner) {
        listing.setOwner(owner);
        listing.setStatus("PENDING");
        return listingRepository.save(listing);
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public List<Listing> getListingsByCategory(String category) {
        return listingRepository.findByCategory(category);
    }

    public Listing approveListing(Long listingId) {
        Listing listing = listingRepository.findById(listingId).orElseThrow();
        listing.setStatus("APPROVED");
        return listingRepository.save(listing);
    }

    public void deleteListing(Long listingId) {
        listingRepository.deleteById(listingId);
    }
}