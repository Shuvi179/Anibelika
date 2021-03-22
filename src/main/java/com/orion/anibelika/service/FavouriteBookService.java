package com.orion.anibelika.service;

import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;

public interface FavouriteBookService {
    void markBookAsFavourite(Long bookId);

    void unMarkBookAsFavourite(Long bookId);

    PaginationAudioBookInfoDTO getFavouriteBooksByPage(Integer pageNumber, Integer numberOfElements);
}
