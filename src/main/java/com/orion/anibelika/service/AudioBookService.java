package com.orion.anibelika.service;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import org.springframework.security.access.prepost.PostAuthorize;

public interface AudioBookService {
    @PostAuthorize("returnObject != null")
    DefaultAudioBookInfoDTO getBookById(Long id);

    void addAudioBook(DefaultAudioBookInfoDTO dto);

    void updateAudioBook(DefaultAudioBookInfoDTO dto);

    PaginationAudioBookInfoDTO getAudioBookPage(Integer pageNumber, Integer numberOfElementsByPage);
}
