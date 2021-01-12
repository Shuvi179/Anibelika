package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.AudioDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.BaseAudio;
import com.orion.anibelika.repository.AudioBookRepository;
import com.orion.anibelika.repository.BaseAudioRepository;
import com.orion.anibelika.service.BaseAudioService;
import org.springframework.stereotype.Service;

@Service
public class BaseAudioServiceImpl implements BaseAudioService {

    private final BaseAudioRepository baseAudioRepository;
    private final AudioBookRepository audioBookRepository;

    public BaseAudioServiceImpl(BaseAudioRepository baseAudioRepository, AudioBookRepository audioBookRepository) {
        this.baseAudioRepository = baseAudioRepository;
        this.audioBookRepository = audioBookRepository;
    }

    @Override
    public Long addNewAudio(AudioDTO audioDTO) {
        //TODO add validation
        AudioBook book = audioBookRepository.getOne(audioDTO.getBookId());
        BaseAudio baseAudio = new BaseAudio();
        baseAudio.setName(audioDTO.getName());
        baseAudio.setBook(book);
        return baseAudioRepository.save(baseAudio).getId();
    }
}
