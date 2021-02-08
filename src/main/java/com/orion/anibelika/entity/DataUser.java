package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "data_user")
public class DataUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name", unique = true)
    private String nickName;

    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<AudioBook> audioBooks;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;

    @OneToOne(mappedBy = "user")
    private AuthUser authUser;
}
