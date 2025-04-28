package com.homehunter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByStatus(String status);
    List<Listing> findByOwnerId(Long ownerId);
    List<Listing> findByCategory(String category);
    List<Listing> findByLocationContaining(String location);
}