package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.helper.EntityToDTOMapper;
import com.orion.anibelika.repository.AudioBookRepository;
import com.orion.anibelika.service.AudioBookService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AudioServiceImpl implements AudioBookService {

    private final AudioBookRepository audioBookRepository;
    private final EntityToDTOMapper entityToDTOMapper;

    public AudioServiceImpl(AudioBookRepository audioBookRepository, EntityToDTOMapper entityToDTOMapper) {
        this.audioBookRepository = audioBookRepository;
        this.entityToDTOMapper = entityToDTOMapper;
    }

    @Override
    public DefaultAudioBookInfoDTO getBookById(@NonNull Long id) {
        return entityToDTOMapper.map(audioBookRepository.getOne(id));
    }
}
