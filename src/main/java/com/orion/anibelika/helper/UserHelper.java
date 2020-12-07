package com.orion.anibelika.helper;

import com.orion.anibelika.entity.DataUser;

public interface UserHelper {
    boolean isCurrentUserAuthenticated();

    DataUser getCurrentDataUser();
}
