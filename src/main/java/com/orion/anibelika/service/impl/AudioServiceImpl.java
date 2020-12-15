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
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;


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
    public DefaultAudioBookInfoDTO getBookById(@NonNull Long id) {
        return mapper.map(audioBookRepository.getOne(id));
    }

    @Override
    @Transactional
    public void addAudioBook(DefaultAudioBookInfoDTO dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new IllegalArgumentException("book id must be null");
        }
        AudioBook book = mapper.map(dto);
        book.setImageURL(urlProvider.getURLById(book.getUser().getId()));
        fileSystemImageProvider.saveImage(book.getImageURL(), dto.getImage());
        audioBookRepository.save(book);
    }

    @Override
    @Transactional
    public void updateAudioBook(DefaultAudioBookInfoDTO dto) {
        if (Objects.isNull(dto.getId()) || dto.getId() <= 0) {
            throw new IllegalArgumentException("book id is incorrect: " + dto.getId());
        }
        AudioBook currentBook = audioBookRepository.getOne(dto.getId());
        if (!currentBook.getUser().getId().equals(userHelper.getCurrentDataUser().getId())) {
            throw new PermissionException("You don't have permission to access this data");
        }
        AudioBook newBook = mapper.map(dto);
        newBook.setImageURL(currentBook.getImageURL());
        fileSystemImageProvider.saveImage(newBook.getImageURL(), dto.getImage());
        audioBookRepository.save(newBook);
    }

    @Override
    @Transactional
    public PaginationAudioBookInfoDTO getAudioBookPage(Integer pageNumber, Integer numberOfElementsByPage) {
        Pageable request = PageRequest.of(pageNumber, numberOfElementsByPage);
        List<AudioBook> result = audioBookRepository.findAll(request).getContent();
        return new PaginationAudioBookInfoDTO(mapper.mapAll(result));
    }
}
