package com.homehunter;

import com.housingfinder.model.Listing;
import com.housingfinder.model.User;
import com.housingfinder.repository.ListingRepository;
import com.housingfinder.service.ListingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListingServiceTest {
    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingService listingService;

    @Test
    public void testCreateListing() {
        Listing listing = new Listing();
        listing.setTitle("Test Listing");
        User owner = new User();
        owner.setId(1L);

        when(listingRepository.save(any())).thenReturn(listing);

        Listing savedListing = listingService.createListing(listing, owner);

        assertNotNull(savedListing);
        assertEquals("PENDING", savedListing.getStatus());
        assertEquals(owner, savedListing.getOwner());
    }

    @Test
    public void testApproveListing() {
        Listing listing = new Listing();
        listing.setId(1L);
        listing.setStatus("PENDING");

        when(listingRepository.findById(1L)).thenReturn(Optional.of(listing));
        when(listingRepository.save(any())).thenReturn(listing);

        Listing approvedListing = listingService.approveListing(1L);

        assertEquals("APPROVED", approvedListing.getStatus());
    }
}