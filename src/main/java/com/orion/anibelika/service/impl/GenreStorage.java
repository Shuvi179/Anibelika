package com.orion.anibelika.service.impl;

import com.orion.anibelika.entity.Genre;
import com.orion.anibelika.repository.GenreRepository;
import com.orion.anibelika.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreStorage implements GenreService {
    private final List<Genre> genres = List.of(
            new Genre(1L, "Сёнен", "Shounen"), new Genre(2L, "Сёнен Ай", "Shounen Ai"),
            new Genre(3L, "Сейнен", "Seinen"), new Genre(4L, "Сёдзе", "Shoujo"),
            new Genre(5L, "Сёдзе Ай", "Shoujo Ai"), new Genre(6L, "Дзёсей", "Josei"),
            new Genre(7L, "Комедия", "Comedy"), new Genre(8L, "Романтика", "Romance"),
            new Genre(9L, "Школа", "School"), new Genre(10L, "Безумие", "Dementia"),
            new Genre(11L, "Боевые искусства", "Martial Arts"), new Genre(12L, "Вампиры", "Vampire"),
            new Genre(13L, "Военное", "Military"), new Genre(14L, "Гарем", "Harem"),
            new Genre(15L, "Демоны", "Demons"), new Genre(16L, "Детектив", "Mystery"),
            new Genre(17L, "Детское", "Kids"), new Genre(18L, "Драма", "Drama"),
            new Genre(19L, "Игры", "Game"), new Genre(20L, "Исторический", "Historical"),
            new Genre(21L, "Космос", "Space"), new Genre(22L, "Магия", "Magic"),
            new Genre(23L, "Машины", "Cars"), new Genre(24L, "Меха", "Mecha"),
            new Genre(25L, "Музыка", "Music"), new Genre(26L, "Пародия", "Parody"),
            new Genre(27L, "Повседневность", "Slice of Life"), new Genre(28L, "Полиция", "Police"),
            new Genre(29L, "Приключения", "Adventure"), new Genre(30L, "Психологическое", "Psychological"),
            new Genre(31L, "Самураи", "Samurai"), new Genre(32L, "Сверхъестественное", "Supernatural"),
            new Genre(33L, "Смена пола", "Gender Bender"), new Genre(34L, "Спорт", "Sport"),
            new Genre(35L, "Супер сила", "Super Power"), new Genre(36L, "Ужасы", "Horror"),
            new Genre(37L, "Фантастика", "Sci-Fi"), new Genre(38L, "Фэнтези", "Fantasy"),
            new Genre(39L, "Экшен", "Action"), new Genre(40L, "Этти", "Ecchi"),
            new Genre(41L, "Триллер", "Thriller"), new Genre(42L, "Хентай", "Hentai"),
            new Genre(43L, "Яой", "Yaoi"), new Genre(44L, "Юри", "Yuri"),
            new Genre(45L, "Додзинси", "Doujinshi")
    );

    private final GenreRepository genreRepository;

    public GenreStorage(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getGenres() {
        return genres;
    }

    private Genre getByName(String name) {
        return genres.stream()
                .filter(genre -> (genre.getNameRus().equals(name) || genre.getNameEng().equals(name)))
                .findFirst().orElseThrow();
    }

    @Override
    public Set<Genre> getAllByNames(List<String> names) {
        return genreRepository.getAllByIdIsIn(getIds(names));
    }

    @Override
    public List<Long> getIds(List<String> names) {
        return names.stream()
                .map(name -> this.getByName(name).getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllNames(Set<Genre> genres) {
        return genres.stream()
                .map(Genre::getNameRus)
                .collect(Collectors.toList());
    }
}
