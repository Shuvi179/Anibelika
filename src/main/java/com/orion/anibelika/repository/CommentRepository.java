package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByBook(AudioBook audioBook, Pageable pageable);
}
