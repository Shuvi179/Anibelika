package com.orion.anibelika.helper;

import com.orion.anibelika.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserHelper {

    private UserHelper() {
    }

    public static String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((User) principal).getUsername();
    }

    public static boolean isCurrentUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
