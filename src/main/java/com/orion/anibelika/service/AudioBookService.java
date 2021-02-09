package com.orion.anibelika.service;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import com.orion.anibelika.entity.AudioBook;

public interface AudioBookService {
    DefaultAudioBookInfoDTO getBookById(Long id);

    AudioBook getPermittedBookEntityById(Long id);

    AudioBook getBookEntityById(Long id);

    void addAudioBook(DefaultAudioBookInfoDTO dto);

    void updateAudioBook(DefaultAudioBookInfoDTO dto);

    PaginationAudioBookInfoDTO getAudioBookPage(Integer pageNumber, Integer numberOfElementsByPage);

    void saveBookImage(Long id, byte[] image);

    byte[] getBookImage(Long id);

    byte[] getSmallBookImage(Long id);
}
