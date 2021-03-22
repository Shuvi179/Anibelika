package com.orion.anibelika.repository;

import com.orion.anibelika.entity.FavouriteBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteBookRepository extends JpaRepository<FavouriteBook, Long> {
    FavouriteBook findByBookId(Long bookId);

    @Override
    Page<FavouriteBook> findAll(Pageable pageable);
}
