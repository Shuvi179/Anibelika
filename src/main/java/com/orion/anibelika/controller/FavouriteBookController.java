package com.orion.anibelika.controller;

import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import com.orion.anibelika.service.FavouriteBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping(value = "/api/v1/book/favourite")
public class FavouriteBookController {
    private final FavouriteBookService favouriteBookService;

    private static final Integer DEFAULT_ELEMENTS_ON_PAGE_NUMBER = 10;

    public FavouriteBookController(FavouriteBookService favouriteBookService) {
        this.favouriteBookService = favouriteBookService;
    }

    @PostMapping("/{bookId}/mark")
    public void markBookAsFavourite(@PathVariable Long bookId) {
        favouriteBookService.markBookAsFavourite(bookId);
    }

    @PostMapping("/{bookId}/unmark")
    public void unMarkBookAsFavourite(@PathVariable Long bookId) {
        favouriteBookService.unMarkBookAsFavourite(bookId);
    }

    @GetMapping("/page/{pageId}")
    public ResponseEntity<PaginationAudioBookInfoDTO> getFavouriteBookPage(@PathVariable @Min(1) Integer pageId) {
        return new ResponseEntity<>(favouriteBookService.getFavouriteBooksByPage(pageId, DEFAULT_ELEMENTS_ON_PAGE_NUMBER), HttpStatus.OK);
    }
}
