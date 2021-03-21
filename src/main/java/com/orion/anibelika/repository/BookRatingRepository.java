package com.orion.anibelika.repository;

import com.orion.anibelika.entity.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRatingRepository extends JpaRepository<BookRating, Long> {
    BookRating findByBookId(Long bookId);
}
