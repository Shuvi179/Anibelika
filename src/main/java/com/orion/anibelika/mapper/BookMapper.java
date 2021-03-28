package com.orion.anibelika.mapper;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.FullAudioBookInfoDTO;
import com.orion.anibelika.dto.RatingDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.BookRating;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.entity.Genre;
import com.orion.anibelika.service.UserHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Component
public class BookMapper {

    private final UserHelper userHelper;
    private final Genre[] genres = Genre.values();

    public BookMapper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    public FullAudioBookInfoDTO map(AudioBook audioBook) {
        return getFullInfoDto(audioBook, getCreatedByCurrentUserFunction());
    }

    public AudioBook map(DefaultAudioBookInfoDTO dto) {
        AudioBook book = new AudioBook();
        book.setName(dto.getName());
        book.setDescription(dto.getDescription());
        book.setTome(dto.getTome());
        book.setUser(userHelper.getCurrentDataUser());
        book.setGenreMask(Genre.getGenreMask(dto.getGenres(), genres));
        return book;
    }

    public List<FullAudioBookInfoDTO> mapAll(List<AudioBook> audioBooks) {
        Predicate<AudioBook> isCreated = getCreatedByCurrentUserFunction();
        return audioBooks.stream()
                .map(book -> getFullInfoDto(book, isCreated))
                .collect(Collectors.toList());
    }

    private FullAudioBookInfoDTO getFullInfoDto(AudioBook audioBook, Predicate<AudioBook> createdByUser) {
        DefaultAudioBookInfoDTO defaultInfo = getDefaultDto(audioBook, createdByUser);
        RatingDTO rating = calculateRating(audioBook.getBookRating());
        return new FullAudioBookInfoDTO(defaultInfo, rating);
    }

    private RatingDTO calculateRating(BookRating rating) {
        if (rating.getNumberOfVotes() == 0L) {
            return new RatingDTO(0., 0L);
        }
        return new RatingDTO(rating.getRatingSum() * 1. / rating.getNumberOfVotes(), rating.getNumberOfVotes());
    }

    private DefaultAudioBookInfoDTO getDefaultDto(AudioBook audioBook, Predicate<AudioBook> createdByUser) {
        DefaultAudioBookInfoDTO dto = new DefaultAudioBookInfoDTO();
        dto.setId(audioBook.getId());
        dto.setTome(audioBook.getTome());
        dto.setName(audioBook.getName());
        dto.setDescription(audioBook.getDescription());
        dto.setCreatedByCurrentUser(createdByUser.test(audioBook));
        dto.setGenres(Genre.getGenresFromMask(audioBook.getGenreMask(), genres));
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
