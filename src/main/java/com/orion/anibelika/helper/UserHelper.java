package com.orion.anibelika.helper;

import com.orion.anibelika.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserHelper {

    private UserHelper() {
    }

    public static String getCurrentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((User) principal).getUsername();
    }

    public static Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((User) principal).getId();
    }

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isCurrentUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
