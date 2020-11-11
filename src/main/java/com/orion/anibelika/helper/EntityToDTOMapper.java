package com.orion.anibelika.helper;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.entity.AudioBook;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.orion.anibelika.helper.UserHelper.getCurrentUser;
import static com.orion.anibelika.helper.UserHelper.isCurrentUserAuthenticated;

@Configuration
public class EntityToDTOMapper {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public DefaultAudioBookInfoDTO map(AudioBook audioBook) {
        DefaultAudioBookInfoDTO dto = modelMapper().map(audioBook, DefaultAudioBookInfoDTO.class);
        if (isCurrentUserAuthenticated()) {
            dto.setCreatedByCurrentUser(audioBook.getUser().getUsername().equals(getCurrentUser()));
        } else {
            dto.setCreatedByCurrentUser(false);
        }
        return dto;
    }
}
