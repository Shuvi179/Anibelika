package com.orion.anibelika.service;

import com.orion.anibelika.dto.NewUserDTO;
import com.orion.anibelika.dto.PasswordResetDTO;
import com.orion.anibelika.dto.UserDTO;
import com.orion.anibelika.entity.DataUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    DataUser addUser(NewUserDTO newUserDTO);

    UserDTO getUserDataById(Long id);

    void confirmUser(String uuid);

    void saveUserImage(Long id, byte[] image);

    byte[] getUserImage(Long id);

    byte[] getSmallUserImage(Long id);

    void startResetPasswordProcess(String email);

    void resetUserPassword(String uuid, PasswordResetDTO dto);
}
