package com.homehunter.service;

import com.homehunter.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getPendingApprovals() {
        return userRepository.findByApprovedFalse();
    }

    public User approveUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setApproved(true);
        return userRepository.save(user);
    }

    public User updateUserRole(Long userId, String role) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setRole(role);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return null;
    }
}