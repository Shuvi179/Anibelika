package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "books")
public class AudioBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    private String photoURL;

    @Column
    private Integer tome;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
