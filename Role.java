package com.homehunter.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public enum Role implements GrantedAuthority {
    ROLE_USER("User", "Basic access rights"),
    ROLE_MODERATOR("Moderator", "Can manage content and users"),
    ROLE_ADMIN("Administrator", "Full system access");

    private final String displayName;
    private final String description;

    Role(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    // Required for Spring Security integration
    @Override
    public String getAuthority() {
        return name(); // Returns "ROLE_USER", "ROLE_ADMIN", etc.
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    // Utility method to get roles as Spring Security authorities
    public static List<GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> (GrantedAuthority) role)
                .collect(Collectors.toList());
    }

    // Get all roles for UI selection
    public static List<Role> getAllRoles() {
        return Arrays.asList(values());
    }

    // Find role by authority name (for security conversions)
    public static Optional<Role> fromAuthority(String authority) {
        return Arrays.stream(values())
                .filter(role -> role.getAuthority().equals(authority))
                .findFirst();
    }
}