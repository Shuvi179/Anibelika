package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.AudioDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.BaseAudio;
import com.orion.anibelika.repository.BaseAudioRepository;
import com.orion.anibelika.service.AudioBookService;
import com.orion.anibelika.service.BaseAudioService;
import org.springframework.stereotype.Service;

@Service
public class BaseAudioServiceImpl implements BaseAudioService {

    private final BaseAudioRepository baseAudioRepository;
    private final AudioBookService audioBookService;

    public BaseAudioServiceImpl(BaseAudioRepository baseAudioRepository, AudioBookService audioBookService) {
        this.baseAudioRepository = baseAudioRepository;
        this.audioBookService = audioBookService;
    }

    @Override
    public Long addNewAudio(AudioDTO audioDTO) {
        AudioBook book = audioBookService.getBookEntityById(audioDTO.getBookId());
        BaseAudio baseAudio = new BaseAudio();
        baseAudio.setName(audioDTO.getName());
        baseAudio.setBook(book);
        return baseAudioRepository.save(baseAudio).getId();
    }
}
