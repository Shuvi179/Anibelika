package com.orion.anibelika.service.impl;

import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.repository.DataUserRepository;
import com.orion.anibelika.security.ApplicationSecurityRole;
import com.orion.anibelika.service.UserHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.orion.anibelika.security.ApplicationSecurityRole.ROLE_ADMIN;

@Service
public class UserHelperImpl implements UserHelper {

    private final DataUserRepository dataUserRepository;

    public UserHelperImpl(DataUserRepository dataUserRepository) {
        this.dataUserRepository = dataUserRepository;
    }

    @Override
    public AuthUser getCurrentUser() {
        if (!isCurrentUserAuthenticated()) {
            throw new PermissionException("No access to this data");
        }
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public boolean isCurrentUserAuthenticated() {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    @Override
    public DataUser getCurrentDataUser() {
        AuthUser currentUser = getCurrentUser();
        return dataUserRepository.getDataUserByAuthUserId(currentUser.getId());
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
        DataUser currentDataUser = dataUserRepository.getDataUserByAuthUserId(currentUser.getId());
        return currentDataUser.getId().equals(id);
    }
}
