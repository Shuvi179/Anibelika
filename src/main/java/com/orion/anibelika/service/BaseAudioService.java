package com.orion.anibelika.service;

import com.orion.anibelika.dto.AudioDTO;
import com.orion.anibelika.dto.BookAudioDTO;

import java.util.List;

public interface BaseAudioService {
    Long addNewAudio(AudioDTO audioDTO, Long bookId);

    List<Long> addNewAudioList(List<AudioDTO> audioDTOList, Long bookId);

    AudioDTO updateAudio(AudioDTO audioDTO);

    List<AudioDTO> updateAudioList(List<AudioDTO> audioDTOList);

    void validateAudioAccess(Long audioId);

    void removeAudio(Long audioId);

    BookAudioDTO getAudioByBook(Long bookId);
}
