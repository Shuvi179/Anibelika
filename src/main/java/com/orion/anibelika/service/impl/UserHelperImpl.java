package com.orion.anibelika.service.impl;

import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.security.ApplicationSecurityRole;
import com.orion.anibelika.service.UserHelper;
import com.orion.anibelika.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.orion.anibelika.security.ApplicationSecurityRole.ROLE_ADMIN;

@Service
public class UserHelperImpl implements UserHelper {

    private final UserService userService;

    public UserHelperImpl(UserService userService) {
        this.userService = userService;
    }

    private AuthUser getCurrentUser() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public boolean isCurrentUserAuthenticated() {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    @Override
    public DataUser getCurrentDataUser() {
        AuthUser currentUser = getCurrentUser();
        return userService.getDataUser(currentUser);
    }

    @Override
    public boolean authenticatedWithId(Long id) {
        if (!isCurrentUserAuthenticated()) {
            return false;
        }
        AuthUser currentUser = getCurrentUser();
        if (currentUser.getRoles().contains(ApplicationSecurityRole.getRole(ROLE_ADMIN))) {
            return true;
        }
        DataUser currentDataUser = userService.getDataUser(currentUser);
        return currentDataUser.getId().equals(id);
    }
}
