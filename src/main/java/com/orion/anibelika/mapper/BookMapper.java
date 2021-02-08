package com.orion.anibelika.mapper;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.service.UserHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Component
public class BookMapper {

    private final UserHelper userHelper;

    public BookMapper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    public DefaultAudioBookInfoDTO map(AudioBook audioBook) {
        return getDefaultDto(audioBook, getCreatedByCurrentUserFunction());
    }

    public AudioBook map(DefaultAudioBookInfoDTO dto) {
        AudioBook book = new AudioBook();
        book.setName(dto.getName());
        book.setDescription(dto.getDescription());
        book.setTome(dto.getTome());
        book.setUser(userHelper.getCurrentDataUser());
        return book;
    }

    public List<DefaultAudioBookInfoDTO> mapAll(List<AudioBook> audioBooks) {
        Predicate<AudioBook> isCreated = getCreatedByCurrentUserFunction();
        return audioBooks.stream()
                .map(book -> getDefaultDto(book, isCreated))
                .collect(Collectors.toList());
    }

    private DefaultAudioBookInfoDTO getDefaultDto(AudioBook audioBook, Predicate<AudioBook> createdByUser) {
        DefaultAudioBookInfoDTO dto = new DefaultAudioBookInfoDTO();
        dto.setId(audioBook.getId());
        dto.setTome(audioBook.getTome());
        dto.setName(audioBook.getName());
        dto.setDescription(audioBook.getDescription());
        dto.setCreatedByCurrentUser(createdByUser.test(audioBook));
        return dto;
    }

    private Predicate<AudioBook> getCreatedByCurrentUserFunction() {
        if (!userHelper.isCurrentUserAuthenticated()) {
            return audioBook -> false;
        } else {
            DataUser user = userHelper.getCurrentDataUser();
            return audioBook -> isCreatedByCurrentUser(user, audioBook);
        }
    }

    private boolean isCreatedByCurrentUser(DataUser dataUser, AudioBook audioBook) {
        return audioBook.getUser().getId().equals(dataUser.getId());
    }
}
