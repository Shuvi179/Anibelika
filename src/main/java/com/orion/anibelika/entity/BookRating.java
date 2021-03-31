package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "book_rating")
public class BookRating {
    @Id
    @Column(name = "book_rating_id")
    private Long id;

    @Column
    private Long ratingSum = 0L;

    @Column
    private Long numberOfVotes = 0L;

    @Column
    private Double rating = 0.;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_rating_id")
    private AudioBook book;

    @OneToMany(mappedBy = "bookRating")
    private Set<BookRatingVote> votes;
}
