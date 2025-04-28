package com.homehunter.service;

import com.homehunter.dto.LoginDTO;
import com.homehunter.model.User;
import com.homehunter.repository.UserRepository;
import com.homehunter.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final ActivityLogService activityLogService;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       JwtTokenProvider tokenProvider,
                       ActivityLogService activityLogService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.activityLogService = activityLogService;
    }

    public String authenticateUser(LoginDTO loginDTO) {
        User user;

        switch (loginDTO.getLoginOption()) {
            case "email":
                user = userRepository.findByEmail(loginDTO.getIdentifier())
                        .orElseThrow(() -> new RuntimeException("User not found with this email"));
                break;
            case "username":
                user = userRepository.findByUsername(loginDTO.getIdentifier())
                        .orElseThrow(() -> new RuntimeException("User not found with this username"));
                break;
            case "phone":
                user = userRepository.findByPhone(loginDTO.getIdentifier())
                        .orElseThrow(() -> new RuntimeException("User not found with this phone number"));
                break;
            default:
                throw new RuntimeException("Invalid login option");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Log login activity
        activityLogService.logActivity(user.getId(), "LOGIN", "User logged in");

        return tokenProvider.generateToken(authentication);
    }
}