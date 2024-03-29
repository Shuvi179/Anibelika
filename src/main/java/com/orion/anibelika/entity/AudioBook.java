package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "books")
public class AudioBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Size(max = 5000)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column
    private Long tome;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @OneToMany(mappedBy = "book")
    private Set<BaseAudio> audios;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private DataUser user;

    @OneToMany(mappedBy = "book")
    private Set<BookHistoryEntity> bookHistory;

    @OneToOne(mappedBy = "book", optional = false)
    @PrimaryKeyJoinColumn
    private BookRating bookRating;

    @Transient
    @ManyToMany(mappedBy = "favouriteBooks")
    private Set<DataUser> users;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Genre> genres;

    @OneToMany(mappedBy = "book")
    private Set<Comment> comments;
}
