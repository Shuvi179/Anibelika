package com.orion.anibelika.service;

import com.orion.anibelika.entity.DataUser;

public interface UserHelper {
    boolean isCurrentUserAuthenticated();

    DataUser getCurrentDataUser();

    boolean authenticatedWithId(Long id);
}
