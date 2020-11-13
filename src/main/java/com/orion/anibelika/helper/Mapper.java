package com.orion.anibelika.helper;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.image.FileSystemImageProvider;
import com.orion.anibelika.url.URLProvider;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.orion.anibelika.helper.UserHelper.*;

@Component
public class Mapper {

    private ModelMapper modelMapper = null;
    private final FileSystemImageProvider fileSystemImageProvider;
    private final URLProvider urlProvider;

    public Mapper(FileSystemImageProvider fileSystemImageProvider, URLProvider urlProvider) {
        this.fileSystemImageProvider = fileSystemImageProvider;
        this.urlProvider = urlProvider;
    }

    private ModelMapper modelMapper() {
        if (Objects.isNull(modelMapper)) {
            modelMapper = new ModelMapper();
        }
        return modelMapper;
    }

    public DefaultAudioBookInfoDTO map(AudioBook audioBook) {
        DefaultAudioBookInfoDTO dto = modelMapper().map(audioBook, DefaultAudioBookInfoDTO.class);
        dto.setImage(fileSystemImageProvider.getImage(audioBook.getImageURL()));
        if (isCurrentUserAuthenticated()) {
            dto.setCreatedByCurrentUser(audioBook.getUser().getUsername().equals(getCurrentUserName()));
        } else {
            dto.setCreatedByCurrentUser(false);
        }
        return dto;
    }

    public AudioBook map(DefaultAudioBookInfoDTO dto) {
        AudioBook book = modelMapper().map(dto, AudioBook.class);
        book.setUser(getCurrentUser());
        book.setImageURL(urlProvider.getURLById(getCurrentUserId()));
        return book;
    }
}
