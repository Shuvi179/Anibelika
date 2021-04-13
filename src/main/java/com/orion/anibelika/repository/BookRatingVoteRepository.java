package com.orion.anibelika.repository;

import com.orion.anibelika.entity.BookRating;
import com.orion.anibelika.entity.BookRatingVote;
import com.orion.anibelika.entity.DataUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRatingVoteRepository extends JpaRepository<BookRatingVote, Long> {
    BookRatingVote findByUserAndBookRating(DataUser user, BookRating bookRating);

    @Query(value = "select brv.rating from BookRatingVote brv join brv.bookRating br where brv.user.id = ?2 and br.book.id = ?1")
    Long findByBookAndUserId(Long bookId, Long userId);
}
