package com.orion.anibelika.repository;

import com.orion.anibelika.entity.BookRatingVote;
import com.orion.anibelika.entity.DataUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRatingVoteRepository extends JpaRepository<BookRatingVote, Long> {
    BookRatingVote findByUser(DataUser user);
}
