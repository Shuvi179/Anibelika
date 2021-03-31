package com.orion.anibelika.service;

import com.orion.anibelika.dto.AudioBookFilterDTO;
import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.FullAudioBookInfoDTO;
import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import com.orion.anibelika.entity.AudioBook;

public interface AudioBookService {
    FullAudioBookInfoDTO getBookById(Long id);

    AudioBook getPermittedBookEntityById(Long id);

    AudioBook getBookEntityById(Long id);

    void addAudioBook(DefaultAudioBookInfoDTO dto);

    void updateAudioBook(DefaultAudioBookInfoDTO dto);

    void validateAudioAccess(Long audioId);

    PaginationAudioBookInfoDTO getAudioBookPage(AudioBookFilterDTO dto, Integer pageNumber, Integer numberOfElementsByPage);

    void saveBookImage(Long id, byte[] image);

    byte[] getBookImage(Long id);

    byte[] getSmallBookImage(Long id);

    void markBookAsFavourite(Long bookId);

    void unMarkBookAsFavourite(Long bookId);

    PaginationAudioBookInfoDTO getFavouriteBooksByPage(Long userId, Integer pageNumber, Integer numberOfElements);
}
