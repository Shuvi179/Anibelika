package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.helper.Mapper;
import com.orion.anibelika.image.FileSystemImageProvider;
import com.orion.anibelika.repository.AudioBookRepository;
import com.orion.anibelika.service.AudioBookService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AudioServiceImpl implements AudioBookService {

    private final AudioBookRepository audioBookRepository;
    private final Mapper mapper;
    private final FileSystemImageProvider fileSystemImageProvider;

    public AudioServiceImpl(AudioBookRepository audioBookRepository, Mapper mapper,
                            FileSystemImageProvider fileSystemImageProvider) {
        this.audioBookRepository = audioBookRepository;
        this.mapper = mapper;
        this.fileSystemImageProvider = fileSystemImageProvider;
    }

    @Override
    public DefaultAudioBookInfoDTO getBookById(@NonNull Long id) {
        return mapper.map(audioBookRepository.getOne(id));
    }

    @Override
    public void addAudioBook(DefaultAudioBookInfoDTO dto) {
        AudioBook book = mapper.map(dto);
        fileSystemImageProvider.saveImage(book.getImageURL(), dto.getImage());
        audioBookRepository.save(book);
    }
}
