package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.CustomUserInfoDTO;
import com.orion.anibelika.dto.NewUserDTO;
import com.orion.anibelika.dto.RegisterUserDTO;
import com.orion.anibelika.dto.UserDTO;
import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.entity.EmailConfirmation;
import com.orion.anibelika.mapper.UserMapper;
import com.orion.anibelika.repository.EmailConfirmationRepository;
import com.orion.anibelika.service.MailSenderService;
import com.orion.anibelika.service.RegistrationService;
import com.orion.anibelika.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Value("${registration.url.confirm}")
    private String registrationURL;

    @Value("${registration.subject}")
    private String subject;

    private final UserService userService;
    private final UserMapper userMapper;
    private final MailSenderService mailSender;
    private final EmailConfirmationRepository confirmationRepository;

    public RegistrationServiceImpl(UserService userService, UserMapper userMapper, MailSenderService mailSender,
                                   EmailConfirmationRepository confirmationRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.mailSender = mailSender;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    @Transactional
    public UserDTO registerUser(RegisterUserDTO dto) {
        return register(userMapper.map(dto));
    }

    @Override
    @Transactional
    public UserDTO registerUser(CustomUserInfoDTO dto) {
        return register(userMapper.map(dto));
    }

    private UserDTO register(NewUserDTO dto) {
        DataUser newUser = userService.addUser(dto);
        sendRegisterMessage(newUser.getAuthUser(), dto.getEmail());
        return userMapper.map(newUser);
    }

    private void sendRegisterMessage(AuthUser user, String email) {
        UUID newUserRegisterIdentification = UUID.randomUUID();
        String message = registrationURL + newUserRegisterIdentification;
        mailSender.sendMessage(email, subject, message);
        saveConfirmation(user, newUserRegisterIdentification);
    }

    private void saveConfirmation(AuthUser user, UUID registerIdentification) {
        EmailConfirmation confirmation = getConfirmation(user);
        confirmation.setUser(user);
        confirmation.setUuid(registerIdentification.toString());
        confirmationRepository.save(confirmation);
    }

    private EmailConfirmation getConfirmation(AuthUser user) {
        EmailConfirmation confirmation = confirmationRepository.getEmailConfirmationByUser(user);
        return Objects.isNull(confirmation) ? new EmailConfirmation() : confirmation;
    }
}
