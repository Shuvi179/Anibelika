package com.orion.anibelika.initialization;

import com.orion.anibelika.repository.GenreRepository;
import com.orion.anibelika.service.impl.GenreStorage;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class GenreLoader implements ApplicationRunner {
    private final GenreStorage genreStorage;
    private final GenreRepository genreRepository;

    public GenreLoader(GenreStorage genreStorage, GenreRepository genreRepository) {
        this.genreStorage = genreStorage;
        this.genreRepository = genreRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!genreRepository.existsById(1L)) {
            genreRepository.saveAll(genreStorage.getGenres());
        }
    }
}
