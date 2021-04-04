package com.orion.anibelika.service;

import com.orion.anibelika.dto.CustomUserInfoDTO;
import com.orion.anibelika.dto.RegisterUserDTO;
import com.orion.anibelika.dto.UserDTO;

public interface RegistrationService {
    UserDTO registerUser(RegisterUserDTO dto);

    UserDTO registerUser(CustomUserInfoDTO dto);

    void resendEmailMessage(String email);
}
