package com.orion.anibelika.mapper;

import com.orion.anibelika.dto.UserDataDTO;
import com.orion.anibelika.entity.DataUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public UserDataDTO map(DataUser user) {
        return modelMapper.map(user, UserDataDTO.class);
    }

}
