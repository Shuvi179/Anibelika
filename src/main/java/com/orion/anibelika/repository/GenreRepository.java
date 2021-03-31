package com.orion.anibelika.repository;

import com.orion.anibelika.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Set<Genre> getAllByIdIsIn(List<Long> ids);
}
