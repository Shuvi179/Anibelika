package com.orion.anibelika.service;

import com.orion.anibelika.dto.*;
import com.orion.anibelika.entity.AudioBook;

public interface AudioBookService {
    FullAudioBookInfoDTO getBookById(Long id);

    AudioBook getPermittedBookEntityById(Long id);

    AudioBook getBookEntityById(Long id);

    void addAudioBook(DefaultAudioBookInfoDTO dto);

    void updateAudioBook(DefaultAudioBookInfoDTO dto);

    PaginationAudioBookInfoDTO getAudioBookPage(AudioBookFilterDTO dto, Integer pageNumber, Integer numberOfElementsByPage);

    void saveBookImage(Long id, byte[] image);

    byte[] getBookImage(Long id);

    byte[] getSmallBookImage(Long id);

    void markBookAsFavourite(Long bookId);

    void unMarkBookAsFavourite(Long bookId);

    void removeAllFavouriteBooksByUser();

    PaginationAudioBookInfoDTO getFavouriteBooksByPage(Integer pageNumber, Integer numberOfElements);

    PaginationAudioBookInfoDTO getBooksHistoryByPage(Integer pageNumber, Integer numberOfElements);

    PaginationAudioBookInfoDTO getMyBooksByPage(Integer pageNumber, Integer numberOfElementsByPage);

    FullFilterDTO getFilterDto();

    MyBookCountDTO getMyBookCountDto();
}
