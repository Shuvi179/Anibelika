package com.orion.anibelika.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public enum Genre {
    SHOUNEN(1, "Сёнен", "Shounen"), SHOUNEN_AI(2, "Сёнен Ай", "Shounen Ai"),
    SEINEN(3, "Сейнен", "Seinen"), SHOUJO(4, "Сёдзе", "Shoujo"),
    SHOUJO_AI(5, "Сёдзе Ай", "Shoujo Ai"), JOSEI(6, "Дзёсей", "Josei"),
    COMEDY(7, "Комедия", "Comedy"), ROMANCE(8, "Романтика", "Romance"),
    SCHOOL(9, "Школа", "School"), DEMENTIA(10, "Безумие", "Dementia"),
    MARTIAL_ARTS(11, "Боевые искусства", "Martial Arts"), VAMPIRE(12, "Вампиры", "Vampire"),
    MILITARY(13, "Военное", "Military"), HAREM(14, "Гарем", "Harem"),
    DEMONS(15, "Демоны", "Demons"), MYSTERY(16, "Детектив", "Mystery"),
    KIDS(17, "Детское", "Kids"), DRAMA(18, "Драма", "Drama"),
    GAME(19, "Игры", "Game"), HISTORICAL(20, "Исторический", "Historical"),
    SPACE(21, "Космос", "Space"), MAGIC(22, "Магия", "Magic"),
    CARS(23, "Машины", "Cars"), MECHA(24, "Меха", "Mecha"),
    MUSIC(25, "Музыка", "Music"), PARODY(26, "Пародия", "Parody"),
    SLICE_OF_LIFE(27, "Повседневность", "Slice of Life"), POLICE(28, "Полиция", "Police"),
    ADVENTURE(29, "Приключения", "Adventure"), PSYCHOLOGICAL(30, "Психологическое", "Psychological"),
    SAMURAI(31, "Самураи", "Samurai"), SUPERNATURAL(32, "Сверхъестественное", "Supernatural"),
    GENDER_BENDER(33, "Смена пола", "Gender Bender"), SPORT(34, "Спорт", "Sport"),
    SUPER_POWER(35, "Супер сила", "Super Power"), HORROR(36, "Ужасы", "Horror"),
    SCI_FI(37, "Фантастика", "Sci-Fi"), FANTASY(38, "Фэнтези", "Fantasy"),
    ACTION(39, "Экшен", "Action"), ECCHI(40, "Этти", "Ecchi"),
    THRILLER(41, "Триллер", "Thriller"), HENTAI(42, "Хентай", "Hentai"),
    YAOI(43, "Яой", "Yaoi"), YURI(44, "Юри", "Yuri"),
    DOUJINSHI(45, "Додзинси", "Doujinshi");

    private final String nameRU;
    private final String nameENG;
    private final Integer id;

    Genre(Integer id, String nameRU, String nameENG) {
        this.id = id;
        this.nameRU = nameRU;
        this.nameENG = nameENG;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getNameENG() {
        return nameENG;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return nameENG;
    }

    public static Genre getGenreByName(Genre[] genres, String name) {
        return Arrays.stream(genres)
                .filter(genre -> (genre.getNameRU().equals(name) || genre.getNameENG().equals(name)))
                .findFirst().orElseThrow();
    }

    public static Genre getGenreById(Genre[] genres, Integer id) {
        return Arrays.stream(genres)
                .filter(genre -> genre.getId().equals(id))
                .findFirst().orElseThrow();
    }

    public static String getGenreMask(List<String> genres, Genre[] allGenres) {
        char[] mask = new char[allGenres.length + 1];
        Arrays.fill(mask, '0');
        genres.forEach(genre -> mask[getGenreByName(allGenres, genre).id] = '1');
        return String.copyValueOf(mask);
    }

    public static List<String> getGenresFromMask(String mask, Genre[] allGenres) {
        List<String> genres = new ArrayList<>();
        IntStream.range(1, mask.length()).forEach(i -> {
            if (mask.charAt(i) == '1') {
                genres.add(getGenreById(allGenres, i).nameRU);
            }
        });
        return genres;
    }
}
