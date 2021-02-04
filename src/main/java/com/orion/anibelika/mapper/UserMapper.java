package com.orion.anibelika.mapper;

import com.orion.anibelika.dto.CustomUserInfoDTO;
import com.orion.anibelika.dto.NewUserDTO;
import com.orion.anibelika.dto.RegisterUserDTO;
import com.orion.anibelika.dto.UserDTO;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.service.impl.login.LoginClientId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public UserDTO map(DataUser user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public NewUserDTO map(CustomUserInfoDTO dto) {
        NewUserDTO userDTO = modelMapper.map(dto, NewUserDTO.class);
        userDTO.setType(dto.getClientId());
        userDTO.setPassword(dto.getClientId());
        return userDTO;
    }

    public NewUserDTO map(RegisterUserDTO dto) {
        NewUserDTO userDTO = modelMapper.map(dto, NewUserDTO.class);
        userDTO.setIdentification(dto.getEmail());
        userDTO.setType(LoginClientId.SIMPLE.getClientId());
        return userDTO;
    }
}
