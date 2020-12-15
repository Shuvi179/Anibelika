package com.orion.anibelika.service;

import com.orion.anibelika.dto.RegisterUserDTO;
import com.orion.anibelika.dto.UserDataDTO;

public interface RegistrationService {
    UserDataDTO registerUser(RegisterUserDTO dto);
}
