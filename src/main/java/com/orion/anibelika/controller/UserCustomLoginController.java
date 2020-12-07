package com.orion.anibelika.controller;

import com.orion.anibelika.service.impl.login.CustomLoginServiceFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oauth2Login")
public class UserCustomLoginController {

    private final CustomLoginServiceFactory loginServiceFactory;

    public UserCustomLoginController(CustomLoginServiceFactory loginServiceFactory) {
        this.loginServiceFactory = loginServiceFactory;
    }

    @GetMapping
    public void getOauth2LoginInfo(@AuthenticationPrincipal OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        loginServiceFactory.getLoginService(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())
                .login(oAuth2AuthenticationToken.getPrincipal().getAttributes());
    }
}
