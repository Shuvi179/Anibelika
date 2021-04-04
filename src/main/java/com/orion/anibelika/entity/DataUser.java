package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "data_user")
public class DataUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name", unique = true)
    private String nickName;

    @OneToMany(mappedBy = "user")
    private Set<AudioBook> audioBooks;

    @OneToMany(mappedBy = "user")
    private Set<BookHistoryEntity> bookHistory;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "user")
    private Set<DonateRequisite> requisites;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private AuthUser authUser;

    @ManyToMany
    private Set<AudioBook> favouriteBooks;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<BookRatingVote> votes;

    public void addFavouriteBook(AudioBook book) {
        this.favouriteBooks.add(book);
    }

    public void removeFavouriteBook(AudioBook book) {
        this.favouriteBooks.remove(book);
    }
}
