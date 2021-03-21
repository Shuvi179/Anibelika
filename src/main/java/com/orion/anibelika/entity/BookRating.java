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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long ratingSum = 0L;

    @Column
    private Long numberOfVotes = 0L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AudioBook book;

    @OneToMany(mappedBy = "bookRating")
    private Set<BookRatingVote> votes;
}
