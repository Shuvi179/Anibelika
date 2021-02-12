package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken getByUuid(String uuid);

    PasswordResetToken getByUser(AuthUser user);

    void deleteByUuid(String uuid);
}
