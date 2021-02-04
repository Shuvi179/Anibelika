package com.orion.anibelika.service;

import com.orion.anibelika.dto.NewUserDTO;
import com.orion.anibelika.dto.UserDTO;
import com.orion.anibelika.entity.AuthUser;
import com.orion.anibelika.entity.DataUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AuthUser addUser(NewUserDTO newUserDTO);

    DataUser getDataUser(AuthUser user);

    UserDTO getUserDataById(Long id);

    void updateUser(UserDTO dto);

    void confirmUser(String uuid);

    void saveUserImage(Long id, byte[] image);

    byte[] getUserImage(Long id);
}
