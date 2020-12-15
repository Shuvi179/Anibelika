package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmation, Long> {
   EmailConfirmation getEmailConfirmationByUser(AuthUser user);
}
