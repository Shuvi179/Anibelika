package com.orion.anibelika.service;

import com.orion.anibelika.entity.AuthUser;

public interface PasswordResetService {
    void createResetEvent(AuthUser user);

    AuthUser validateResetToken(String uuid);

    void deleteResetToken(String uuid);
}
