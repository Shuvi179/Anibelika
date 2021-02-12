package com.orion.anibelika.service;

import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;

public interface UserHelper {
    boolean isCurrentUserAuthenticated();

    DataUser getCurrentDataUser();

    AuthUser getCurrentUser();

    boolean authenticatedWithId(Long id);
}
