package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "auth_user")
public class AuthUser implements UserDetails {
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column(name = "identification_name", length = 64)
    private String identificationName;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(nullable = false)
    private boolean confirmed;

    @MapsId
    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private DataUser user;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private EmailConfirmation emailConfirmation;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private PasswordResetToken passwordResetToken;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return confirmed;
    }
}
