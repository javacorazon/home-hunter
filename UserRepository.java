package com.homehunter.repository;

import com.homehunter.entity.User;
import com.homehunter.entity.Role;  // Import the Role enum
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByApprovedFalse();

    // Updated: Use Role enum instead of String
    List<User> findByRole(Role role);  // <-- Key fix here
}