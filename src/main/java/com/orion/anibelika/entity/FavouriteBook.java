package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AudioBook book;

    @Transient
    @ManyToMany(mappedBy = "favouriteBooks")
    private Set<DataUser> users;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FavouriteBook)) {
            return false;
        }
        FavouriteBook favouriteBook = (FavouriteBook) o;
        return this.id.equals(favouriteBook.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
