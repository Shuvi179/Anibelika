package com.orion.anibelika.repository;

import com.orion.anibelika.entity.AudioBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioBookRepository extends JpaRepository<AudioBook, Long> {
    @Override
    Page<AudioBook> findAll(Pageable pageable);
}
