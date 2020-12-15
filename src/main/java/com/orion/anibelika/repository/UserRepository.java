package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository<T extends AuthUser> extends JpaRepository<T, Long> {
    T findUserByIdentificationName(String identification);

    T findUserByEmailConfirmationUuid(String uuid);
}
