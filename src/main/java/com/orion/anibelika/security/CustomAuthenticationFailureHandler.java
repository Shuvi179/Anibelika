package com.orion.anibelika.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) {

        if (exception instanceof DisabledException) {
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}