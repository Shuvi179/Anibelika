package com.orion.anibelika.service;

import com.orion.anibelika.entity.Genre;

import java.util.List;
import java.util.Set;

public interface GenreService {
    List<Genre> getGenres();

    Set<Genre> getAllByNames(List<String> names);

    List<String> getAllNames(Set<Genre> genres);
}
