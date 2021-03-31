package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "genres")
public class Genre {
    @Id
    private Long id;

    @Column
    private String nameRus;

    @Column
    private String nameEng;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<AudioBook> books;

    public Genre(Long id, String nameRus, String nameEng) {
        this.id = id;
        this.nameRus = nameRus;
        this.nameEng = nameEng;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Genre)) {
            return false;
        }
        Genre genre = (Genre) o;
        return this.id.equals(genre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
