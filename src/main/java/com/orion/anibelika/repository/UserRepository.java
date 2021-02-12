package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findUserByIdentificationNameAndType(String identification, String type);

    AuthUser findUserByEmail(String email);

    AuthUser findUserByEmailConfirmationUuid(String uuid);

    boolean existsUserByEmail(String email);
}
