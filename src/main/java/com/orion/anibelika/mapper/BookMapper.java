package com.orion.anibelika.mapper;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.FullAudioBookInfoDTO;
import com.orion.anibelika.dto.RatingDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.repository.AudioBookRepository;
import com.orion.anibelika.service.GenreService;
import com.orion.anibelika.service.UserHelper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Component
public class BookMapper {

    private final UserHelper userHelper;
    private final GenreService genreService;
    private final AudioBookRepository audioBookRepository;

    public BookMapper(UserHelper userHelper, GenreService genreService, AudioBookRepository audioBookRepository) {
        this.userHelper = userHelper;
        this.genreService = genreService;
        this.audioBookRepository = audioBookRepository;
    }

    public FullAudioBookInfoDTO map(AudioBook audioBook) {
        FullAudioBookInfoDTO dto = getFullInfoDto(audioBook, getCreatedByCurrentUserFunction());
        dto.setFavouriteByCurrentUser(isBookFavourite(audioBook.getId()));
        return dto;
    }

    public AudioBook map(DefaultAudioBookInfoDTO dto) {
        AudioBook book = new AudioBook();
        book.setId(dto.getId());
        book.setName(dto.getName());
        book.setDescription(dto.getDescription());
        book.setTome(dto.getTome());
        book.setLastUpdate(new Date());
        book.setUser(userHelper.getCurrentDataUser());
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
        RatingDTO rating = new RatingDTO(audioBook.getBookRating().getRating(), audioBook.getBookRating().getNumberOfVotes());
        return new FullAudioBookInfoDTO(defaultInfo, rating, false);
    }

    private DefaultAudioBookInfoDTO getDefaultDto(AudioBook audioBook, Predicate<AudioBook> createdByUser) {
        DefaultAudioBookInfoDTO dto = new DefaultAudioBookInfoDTO();
        dto.setId(audioBook.getId());
        dto.setTome(audioBook.getTome());
        dto.setName(audioBook.getName());
        dto.setDescription(audioBook.getDescription());
        dto.setCreatedByCurrentUser(createdByUser.test(audioBook));
        dto.setGenres(genreService.getAllNames(audioBook.getGenres()));
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

    private Boolean isBookFavourite(Long bookId) {
        if (!userHelper.isCurrentUserAuthenticated()) {
            return false;
        } else {
            DataUser user = userHelper.getCurrentDataUser();
            return audioBookRepository.isBookFavouriteByUser(user.getId(), bookId) >= 1;
        }
    }

    private boolean isCreatedByCurrentUser(DataUser dataUser, AudioBook audioBook) {
        return audioBook.getUser().getId().equals(dataUser.getId());
    }
}
