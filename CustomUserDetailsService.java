package com.homehunter.security;

import com.homehunter.model.User;
import com.homehunter.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmailOrId)
            throws UsernameNotFoundException {
        // Let's allow login with either username, email, or ID
        User user = userRepository.findByUsername(usernameOrEmailOrId)
                .orElseGet(() -> userRepository.findByEmail(usernameOrEmailOrId)
                        .orElseGet(() -> userRepository.findById(Long.parseLong(usernameOrEmailOrId))
                                .orElseThrow(() -> new UsernameNotFoundException("User not found with identifier: " + usernameOrEmailOrId))));

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id: " + id)
        );

        return UserPrincipal.create(user);
    }
}