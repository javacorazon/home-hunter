package com.homehunter.service;

import com.homehunter.model.User;
import com.homehunter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class NotificationService {
    private final UserRepository userRepository;
    private final ListingService listingService;

    private final String[] notifications = {
            "Hey %s, check out Hotel Africana's luxurious space this Valentine!",
            "Hey %s, checkout the latest hostel \"Chief's Stead\" in Kampala, Makindye west division.",
            "New listing alert: %s, a beautiful %s is now available in %s!",
            "Price drop: %s in %s is now available at a discounted rate!"
    };

    public NotificationService(UserRepository userRepository, ListingService listingService) {
        this.userRepository = userRepository;
        this.listingService = listingService;
    }

    public List<String> getPersonalizedNotifications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ListingDTO> popularListings = listingService.getPopularListings(null);
        if (popularListings.isEmpty()) {
            return List.of("No new notifications");
        }

        Random random = new Random();
        List<String> result = new ArrayList<>();

        // Generate 2-3 random notifications
        int count = 2 + random.nextInt(2);
        for (int i = 0; i < count; i++) {
            ListingDTO listing = popularListings.get(random.nextInt(popularListings.size()));
            String notification;

            switch (random.nextInt(4)) {
                case 0:
                    notification = String.format(notifications[0], user.getFirstName());
                    break;
                case 1:
                    notification = String.format(notifications[1], user.getFirstName());
                    break;
                case 2:
                    notification = String.format(notifications[2],
                            user.getFirstName(),
                            listing.getCategory(),
                            listing.getLocation());
                    break;
                case 3:
                    notification = String.format(notifications[3],
                            listing.getTitle(),
                            listing.getLocation());
                    break;
                default:
                    notification = "New updates available!";
            }

            result.add(notification);
        }

        return result;
    }
}