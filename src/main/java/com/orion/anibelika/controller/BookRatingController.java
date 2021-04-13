package com.orion.anibelika.controller;

import com.orion.anibelika.dto.RatingDTO;
import com.orion.anibelika.service.BookRatingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping(value = "/api/v1/book/{bookId}/rating")
public class BookRatingController {
    private final BookRatingService bookRatingService;

    public BookRatingController(BookRatingService bookRatingService) {
        this.bookRatingService = bookRatingService;
    }

    @GetMapping
    @Operation(summary = "Get book rating")
    public ResponseEntity<RatingDTO> getBookRating(@PathVariable Long bookId) {
        return new ResponseEntity<>(bookRatingService.getRating(bookId), HttpStatus.OK);
    }

    @PostMapping("/{rating}")
    @Operation(summary = "Vote for Book")
    public ResponseEntity<RatingDTO> voteForBook(@PathVariable Long bookId, @PathVariable @Min(0) @Max(5) Long rating) {
        return new ResponseEntity<>(bookRatingService.voteForBook(bookId, rating), HttpStatus.OK);
    }

    @GetMapping("/vote")
    @Operation(summary = "Get rating vote for book")
    public ResponseEntity<Long> getUserVoteByBook(@PathVariable Long bookId) {
        return new ResponseEntity<>(bookRatingService.getUserVoteByBook(bookId), HttpStatus.OK);
    }
}
