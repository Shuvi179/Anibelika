package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "email_confirmation")
public class EmailConfirmation {
    @Id
    @Column(name = "confirmation_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confirmation_id")
    private AuthUser user;
}
