package com.project.carshare.user.infrastructurte.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserUtility {

    public boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(i -> i.getAuthority().equals("ADMIN"));
    }

    private UUID getUserId() {
        var id = SecurityContextHolder.getContext().getAuthentication().getName();
        return UUID.fromString(id);
    }
}
