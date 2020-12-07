package com.orion.anibelika.helper.impl;

import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.helper.UserHelper;
import com.orion.anibelika.repository.DataUserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserHelperImpl implements UserHelper {

    private final DataUserRepository dataUserRepository;

    public UserHelperImpl(DataUserRepository dataUserRepository) {
        this.dataUserRepository = dataUserRepository;
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
        return dataUserRepository.getDataUserByAuthUser(currentUser);
    }
}
