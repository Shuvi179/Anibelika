package com.orion.anibelika.service.impl;

import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.PasswordResetToken;
import com.orion.anibelika.exception.ResetPasswordTokenException;
import com.orion.anibelika.repository.PasswordResetRepository;
import com.orion.anibelika.service.MailSenderService;
import com.orion.anibelika.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private static final int EXPIRATION_HOUR = 1;

    @Value("${reset.password.subject}")
    private String subject;

    @Value("${reset.password.url}")
    private String resetPasswordUrl;

    private final PasswordResetRepository passwordResetRepository;
    private final MailSenderService mailSenderService;

    public PasswordResetServiceImpl(PasswordResetRepository passwordResetRepository,
                                    MailSenderService mailSenderService) {
        this.passwordResetRepository = passwordResetRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public void createResetEvent(AuthUser user) {
        PasswordResetToken token = getToken(user);

        token.setUuid(UUID.randomUUID().toString());

        Calendar expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.HOUR_OF_DAY, EXPIRATION_HOUR);

        token.setExpireTime(expirationTime.getTime());
        token.setUser(user);

        passwordResetRepository.save(token);
        sendPasswordResetEmail(user.getEmail(), token.getUuid());
    }

    private PasswordResetToken getToken(AuthUser user) {
        PasswordResetToken token = passwordResetRepository.getByUser(user);
        return Objects.isNull(token) ? new PasswordResetToken() : token;
    }

    @Override
    public AuthUser validateResetToken(String uuid) {
        PasswordResetToken token = passwordResetRepository.getByUuid(uuid);
        if (Objects.isNull(token)) {
            throw new ResetPasswordTokenException("No user with uuid: " + uuid);
        }
        if (Calendar.getInstance().after(token.getExpireTime())) {
            throw new ResetPasswordTokenException("Token is expired");
        }
        return token.getUser();
    }

    @Override
    public void deleteResetToken(String uuid) {
        passwordResetRepository.deleteByUuid(uuid);
    }

    private void sendPasswordResetEmail(String email, String uuid) {
        String message = resetPasswordUrl + uuid;
        mailSenderService.sendMessage(email, subject, message);
    }
}
