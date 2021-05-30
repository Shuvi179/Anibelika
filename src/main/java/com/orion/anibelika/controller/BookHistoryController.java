package com.orion.anibelika.controller;

import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import com.orion.anibelika.service.AudioBookService;
import com.orion.anibelika.service.BookHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;


@RestController
@RequestMapping("/api/v1/book/history")
@Validated
public class BookHistoryController {


    private final AudioBookService audioBookService;
    private final BookHistoryService bookHistoryService;

    public BookHistoryController(AudioBookService audioBookService, BookHistoryService bookHistoryService) {
        this.audioBookService = audioBookService;
        this.bookHistoryService = bookHistoryService;
    }

    @GetMapping(value = "/page/{pageId}")
    @Operation(summary = "Get page of last viewed books")
    public ResponseEntity<PaginationAudioBookInfoDTO> getLastViewedBooksForPage(@PathVariable @Min(1) Integer pageId) {
        return new ResponseEntity<>(audioBookService.getBooksHistoryByPage(pageId, AudioBookController.DEFAULT_ELEMENTS_ON_PAGE_NUMBER), HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Remove all history for current user")
    public void removeAllByUser() {
        bookHistoryService.removeAllByUser();
    }

    @DeleteMapping(value = "/{bookId}")
    @Operation(summary = "Remove book history by bookId")
    public void removeBookHistoryById(@Min(1) @PathVariable Long bookId) {
        bookHistoryService.removeUserHistory(bookId);
    }
}
