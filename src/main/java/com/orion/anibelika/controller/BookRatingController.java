package com.orion.anibelika.controller;

import com.orion.anibelika.dto.RatingDTO;
import com.orion.anibelika.service.BookRatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Controller
@Validated
@RequestMapping(value = "/api/v1/book/{bookId}/rating")
public class BookRatingController {
    private final BookRatingService bookRatingService;

    public BookRatingController(BookRatingService bookRatingService) {
        this.bookRatingService = bookRatingService;
    }

    @GetMapping
    public ResponseEntity<RatingDTO> getBookRating(@PathVariable Long bookId) {
        return new ResponseEntity<>(bookRatingService.getRating(bookId), HttpStatus.OK);
    }

    @PostMapping(value = "{rating}")
    public ResponseEntity<RatingDTO> voteForBook(@PathVariable Long bookId, @PathVariable @Min(0) @Max(5) Long rating) {
        return new ResponseEntity<>(bookRatingService.voteForBook(bookId, rating), HttpStatus.OK);
    }
}
