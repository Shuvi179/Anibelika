package com.orion.anibelika.service;

import com.orion.anibelika.dto.AudioDTO;

public interface BaseAudioService {
    Long addNewAudio(AudioDTO audioDTO, Long bookId);
}
