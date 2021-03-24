package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {
    @Id
    @Column(name = "reset_token_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reset_token_id")
    private AuthUser user;
}
