package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.image.FileSystemImageProvider;
import com.orion.anibelika.mapper.BookMapper;
import com.orion.anibelika.repository.AudioBookRepository;
import com.orion.anibelika.service.AudioBookService;
import com.orion.anibelika.service.UserHelper;
import com.orion.anibelika.url.URLProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class AudioServiceImpl implements AudioBookService {

    private final AudioBookRepository audioBookRepository;
    private final BookMapper mapper;
    private final FileSystemImageProvider fileSystemImageProvider;
    private final URLProvider urlProvider;
    private final UserHelper userHelper;

    public AudioServiceImpl(AudioBookRepository audioBookRepository, BookMapper mapper,
                            FileSystemImageProvider fileSystemImageProvider, URLProvider urlProvider,
                            UserHelper userHelper) {
        this.audioBookRepository = audioBookRepository;
        this.mapper = mapper;
        this.fileSystemImageProvider = fileSystemImageProvider;
        this.urlProvider = urlProvider;
        this.userHelper = userHelper;
    }

    @Override
    public DefaultAudioBookInfoDTO getBookById(Long id) {
        return mapper.map(validateGetById(id));
    }

    @Override
    public AudioBook getBookEntityById(Long id) {
        return validateBookById(id);
    }

    @Override
    @Transactional
    public void addAudioBook(@NotNull DefaultAudioBookInfoDTO dto) {
        AudioBook book = validateAddBook(dto);
        book.setImageURL(urlProvider.getURLById(book.getUser().getId()));
        fileSystemImageProvider.saveImage(book.getImageURL(), dto.getImage());
        audioBookRepository.save(book);
    }

    private AudioBook validateAddBook(@NotNull DefaultAudioBookInfoDTO dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new IllegalArgumentException("book id must be null");
        }
        if (userHelper.isCurrentUserAuthenticated()) {
            throw new PermissionException("User need to log in");
        }
        return mapper.map(dto);
    }

    @Override
    @Transactional
    public void updateAudioBook(@NotNull DefaultAudioBookInfoDTO dto) {
        AudioBook currentBook = validateBookById(dto.getId());
        AudioBook newBook = mapper.map(dto);
        newBook.setImageURL(currentBook.getImageURL());
        fileSystemImageProvider.saveImage(newBook.getImageURL(), dto.getImage());
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
        Pageable request = PageRequest.of(pageNumber, numberOfElementsByPage);
        List<AudioBook> result = audioBookRepository.findAll(request).getContent();
        return new PaginationAudioBookInfoDTO(mapper.mapAll(result));
    }
}
