package com.orion.anibelika.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public enum Genre {
    TU(1, "tu"), RU(2, "ru");

    private final String name;
    private final Integer id;

    Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Genre getGenreByName(Genre[] genres, String name) {
        return Arrays.stream(genres)
                .filter(genre -> genre.getName().equals(name))
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
                genres.add(getGenreById(allGenres, i).name);
            }
        });
        return genres;
    }
}
