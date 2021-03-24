package com.orion.anibelika.controller;

import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import com.orion.anibelika.service.AudioBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping(value = "/api/v1/book/favourite")
public class FavouriteBookController {
    private final AudioBookService audioBookService;

    private static final Integer DEFAULT_ELEMENTS_ON_PAGE_NUMBER = 10;

    public FavouriteBookController(AudioBookService audioBookService) {
        this.audioBookService = audioBookService;
    }

    @PostMapping("/{bookId}/mark")
    public void markBookAsFavourite(@PathVariable Long bookId) {
        audioBookService.markBookAsFavourite(bookId);
    }

    @PostMapping("/{bookId}/unmark")
    public void unMarkBookAsFavourite(@PathVariable Long bookId) {
        audioBookService.unMarkBookAsFavourite(bookId);
    }

    @GetMapping("/user/{userId}/page/{pageId}")
    public ResponseEntity<PaginationAudioBookInfoDTO> getFavouriteBookPage(@PathVariable Long userId, @PathVariable @Min(1) Integer pageId) {
        return new ResponseEntity<>(audioBookService.getFavouriteBooksByPage(userId, pageId, DEFAULT_ELEMENTS_ON_PAGE_NUMBER), HttpStatus.OK);
    }
}
