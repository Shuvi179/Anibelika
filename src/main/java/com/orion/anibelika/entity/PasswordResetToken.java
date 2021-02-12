package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AuthUser user;
}
