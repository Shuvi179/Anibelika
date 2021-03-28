package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AudioBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AudioBookRepository extends JpaRepository<AudioBook, Long> {
    @Override
    @Query(value = "select b from AudioBook as b join fetch b.bookRating join fetch b.user",
            countQuery = "select count(b) from AudioBook as b")
    Page<AudioBook> findAll(Pageable pageable);

    @Query(value = "select b from DataUser as u join u.audioBooks b join fetch b.bookRating br where u.id = ?1",
            countQuery = "select u.audioBooks.size from DataUser as u where u.id = ?1")
    Page<AudioBook> findAllByUser(Long userId, Pageable pageable);

    @Query(value = "select count(b) from AudioBook as b join b.user as u join b.audios as a where a.id = ?1 and u.id = ?2")
    Integer countBookByUserAndAudio(Long audioId, Long userId);
}
