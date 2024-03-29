package com.orion.anibelika.mapper;

import com.orion.anibelika.dto.CustomUserInfoDTO;
import com.orion.anibelika.dto.NewUserDTO;
import com.orion.anibelika.dto.RegisterUserDTO;
import com.orion.anibelika.dto.UserDTO;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.service.impl.login.LoginClientId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {

    public UserDTO map(DataUser user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getAuthUser().getEmail());
        dto.setNickName(user.getNickName());
        return dto;
    }

    public UserDTO mapUnAuthorize(DataUser user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNickName(user.getNickName());
        return dto;
    }

    public NewUserDTO map(CustomUserInfoDTO dto) {
        NewUserDTO userDTO = new NewUserDTO();
        userDTO.setEmail(dto.getEmail());
        userDTO.setIdentification(dto.getIdentification());
        userDTO.setNickName(dto.getNickName());
        userDTO.setType(dto.getClientId());
        userDTO.setPassword(UUID.randomUUID().toString());
        return userDTO;
    }

    public NewUserDTO map(RegisterUserDTO dto) {
        NewUserDTO userDTO = new NewUserDTO();
        userDTO.setEmail(dto.getEmail());
        userDTO.setIdentification(null);
        userDTO.setNickName(dto.getNickName());
        userDTO.setType(LoginClientId.SIMPLE.getClientId());
        userDTO.setPassword(dto.getPassword());
        return userDTO;
    }
}
