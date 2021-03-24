package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.image.ImageService;
import com.orion.anibelika.mapper.BookMapper;
import com.orion.anibelika.repository.AudioBookRepository;
import com.orion.anibelika.repository.DataUserRepository;
import com.orion.anibelika.service.AudioBookService;
import com.orion.anibelika.service.BookRatingService;
import com.orion.anibelika.service.UserHelper;
import com.orion.anibelika.url.URLPrefix;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AudioBookServiceImpl implements AudioBookService {

    private final AudioBookRepository audioBookRepository;
    private final BookMapper mapper;
    private final UserHelper userHelper;
    private final ImageService imageService;
    private final BookRatingService bookRatingService;
    private final DataUserRepository dataUserRepository;

    public AudioBookServiceImpl(AudioBookRepository audioBookRepository, BookMapper mapper,
                                UserHelper userHelper, ImageService imageService, BookRatingService bookRatingService,
                                DataUserRepository dataUserRepository) {
        this.audioBookRepository = audioBookRepository;
        this.mapper = mapper;
        this.userHelper = userHelper;
        this.imageService = imageService;
        this.bookRatingService = bookRatingService;
        this.dataUserRepository = dataUserRepository;
    }

    @Override
    public DefaultAudioBookInfoDTO getBookById(Long id) {
        return mapper.map(validateGetById(id));
    }

    @Override
    public AudioBook getPermittedBookEntityById(Long id) {
        return validateBookById(id);
    }

    @Override
    public AudioBook getBookEntityById(Long id) {
        return validateGetById(id);
    }

    @Override
    @Transactional
    public void addAudioBook(@NotNull DefaultAudioBookInfoDTO dto) {
        AudioBook book = validateAddBook(dto);
        AudioBook saved = audioBookRepository.save(book);
        bookRatingService.createBookRating(saved);
        saveBookImage(saved.getId(), dto.getImage());
    }

    private AudioBook validateAddBook(@NotNull DefaultAudioBookInfoDTO dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new IllegalArgumentException("book id must be null");
        }
        if (!userHelper.isCurrentUserAuthenticated()) {
            throw new PermissionException("User need to log in");
        }
        return mapper.map(dto);
    }

    @Override
    @Transactional
    public void updateAudioBook(@NotNull DefaultAudioBookInfoDTO dto) {
        AudioBook newBook = mapper.map(dto);
        audioBookRepository.save(newBook);
    }

    private AudioBook validateBookById(Long id) {
        AudioBook currentBook = validateGetById(id);
        if (!userHelper.authenticatedWithId(currentBook.getUser().getId())) {
            throw new PermissionException("You don't have permission to access this data");
        }
        return currentBook;
    }

    private AudioBook validateGetById(Long id) {
        if (Objects.isNull(id) || id <= 0) {
            throw new IllegalArgumentException("book id is incorrect: " + id);
        }
        Optional<AudioBook> currentBook = audioBookRepository.findById(id);
        if (currentBook.isEmpty()) {
            throw new IllegalArgumentException("book id is incorrect: " + id);
        }
        return currentBook.get();
    }

    @Override
    @Transactional
    public PaginationAudioBookInfoDTO getAudioBookPage(Integer pageNumber, Integer numberOfElementsByPage) {
        Pageable request = PageRequest.of(pageNumber - 1, numberOfElementsByPage);
        List<AudioBook> result = audioBookRepository.findAll(request).getContent();
        return new PaginationAudioBookInfoDTO(mapper.mapAll(result));
    }

    @Override
    public void saveBookImage(Long id, byte[] image) {
        validateBookById(id);
        imageService.saveImage(URLPrefix.BOOK, id, image);
        imageService.saveSmallImage(URLPrefix.BOOK, id, image);
    }

    @Override
    public byte[] getBookImage(Long id) {
        return imageService.getImage(URLPrefix.BOOK, id);
    }

    @Override
    public byte[] getSmallBookImage(Long id) {
        return imageService.getSmallImage(URLPrefix.BOOK, id);
    }

    @Override
    @Transactional
    public void markBookAsFavourite(Long bookId) {
        DataUser user = userHelper.getCurrentDataUser();
        AudioBook book = validateGetById(bookId);
        user.addFavouriteBook(book);
        dataUserRepository.save(user);
    }

    @Override
    @Transactional
    public void unMarkBookAsFavourite(Long bookId) {
        DataUser user = userHelper.getCurrentDataUser();
        AudioBook book = validateGetById(bookId);
        user.removeFavouriteBook(book);
        dataUserRepository.save(user);
    }

    @Override
    public PaginationAudioBookInfoDTO getFavouriteBooksByPage(Long userId, Integer pageNumber, Integer numberOfElements) {
        Pageable request = PageRequest.of(pageNumber - 1, numberOfElements);
        List<AudioBook> favouriteAudioBooks = audioBookRepository.findAllByUser(userId, request).getContent();
        return new PaginationAudioBookInfoDTO(mapper.mapAll(favouriteAudioBooks));
    }
}
