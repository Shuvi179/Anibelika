package com.orion.anibelika.service;


import com.orion.anibelika.dto.RatingDTO;
import com.orion.anibelika.entity.AudioBook;

public interface BookRatingService {
    RatingDTO voteForBook(Long bookId, Long rating);

    RatingDTO getRating(Long bookId);

    Long getUserVoteByBook(Long bookId);

    void createBookRating(AudioBook audioBook);
}
