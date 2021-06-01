package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c from Comment c join fetch c.ratingVote join fetch c.user u join fetch u.authUser au join fetch au.roles where c.book = ?1",
            countQuery = "select count(c) from Comment c where c.book = ?1")
    Page<Comment> findAllByBook(AudioBook audioBook, Pageable pageable);
}
