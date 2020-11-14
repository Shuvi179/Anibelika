package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.helper.Mapper;
import com.orion.anibelika.image.FileSystemImageProvider;
import com.orion.anibelika.repository.AudioBookRepository;
import com.orion.anibelika.service.AudioBookService;
import com.orion.anibelika.url.URLProvider;
import lombok.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.orion.anibelika.helper.UserHelper.getCurrentUserId;

@Service
public class AudioServiceImpl implements AudioBookService {

    private final AudioBookRepository audioBookRepository;
    private final Mapper mapper;
    private final FileSystemImageProvider fileSystemImageProvider;
    private final URLProvider urlProvider;

    public AudioServiceImpl(AudioBookRepository audioBookRepository, Mapper mapper,
                            FileSystemImageProvider fileSystemImageProvider, URLProvider urlProvider) {
        this.audioBookRepository = audioBookRepository;
        this.mapper = mapper;
        this.fileSystemImageProvider = fileSystemImageProvider;
        this.urlProvider = urlProvider;
    }

    @Override
    public DefaultAudioBookInfoDTO getBookById(@NonNull Long id) {
        return mapper.map(audioBookRepository.getOne(id));
    }

    @Override
    @PreAuthorize("#dto.id == null")
    public void addAudioBook(DefaultAudioBookInfoDTO dto) {
        if (!validateInputData(dto)) {
            throw new IllegalArgumentException("Incorrect input data");
        }
        AudioBook book = mapper.map(dto);
        book.setImageURL(urlProvider.getURLById(getCurrentUserId()));
        fileSystemImageProvider.saveImage(book.getImageURL(), dto.getImage());
        audioBookRepository.save(book);
    }

    @Override
    @PreAuthorize("#dto.id != null && dto.id > 0")
    public void updateAudioBook(DefaultAudioBookInfoDTO dto) {
        if (!validateInputData(dto)) {
            throw new IllegalArgumentException("Incorrect input data");
        }
        AudioBook currentBook = audioBookRepository.getOne(dto.getId());
        if (!currentBook.getUser().getId().equals(getCurrentUserId())) {
            throw new PermissionException("You don't have permission to access this data");
        }
        AudioBook newBook = mapper.map(dto);
        newBook.setImageURL(currentBook.getImageURL());
        fileSystemImageProvider.saveImage(newBook.getImageURL(), dto.getImage());
        audioBookRepository.save(newBook);
    }

    private boolean validateInputData(DefaultAudioBookInfoDTO dto) {
        return !StringUtils.isEmpty(dto.getName()) && !StringUtils.isEmpty(dto.getDescription())
                && Objects.nonNull(dto.getTome()) && dto.getTome() > 0
                && Objects.nonNull(dto.getImage()) && dto.getImage().length != 0;
    }
}
