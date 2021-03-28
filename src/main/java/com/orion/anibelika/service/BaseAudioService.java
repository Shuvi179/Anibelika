package com.orion.anibelika.service;

import com.orion.anibelika.dto.AudioDTO;
import com.orion.anibelika.dto.BookAudioDTO;

public interface BaseAudioService {
    Long addNewAudio(AudioDTO audioDTO, Long bookId);

    AudioDTO updateAudio(AudioDTO audioDTO);

    void removeAudio(Long audioId);

    BookAudioDTO getAudioByBook(Long bookId);
}
