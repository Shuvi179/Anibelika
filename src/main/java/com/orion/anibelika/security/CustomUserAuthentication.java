package com.orion.anibelika.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserAuthentication implements Authentication {
    private String name;
    private Object details;
    private UserDetails user;
    private boolean authenticated;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserAuthentication(UserDetails user, Object details) {
        this.name = user.getUsername();
        this.details = details;
        this.user = user;
        this.authorities = user.getAuthorities();
        authenticated = true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}