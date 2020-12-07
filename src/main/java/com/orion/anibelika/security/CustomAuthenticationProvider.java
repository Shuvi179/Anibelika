package com.orion.anibelika.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) {
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public Authentication trust(UserDetails user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Authentication trustedAuthentication = new CustomUserAuthentication(user, authentication.getDetails());
        authentication = authenticate(trustedAuthentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}