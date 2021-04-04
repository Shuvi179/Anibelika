package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "book_history")
public class BookHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private DataUser user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private AudioBook book;
}
