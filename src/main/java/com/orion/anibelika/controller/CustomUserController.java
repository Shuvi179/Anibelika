package com.orion.anibelika.controller;

import com.orion.anibelika.dto.CustomUserInfoDTO;
import com.orion.anibelika.dto.UserDTO;
import com.orion.anibelika.service.CustomLoginService;
import com.orion.anibelika.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/oauthLogin")
public class CustomUserController {

    private final CustomLoginService loginService;
    private final RegistrationService registrationService;

    public CustomUserController(CustomLoginService loginService, RegistrationService registrationService) {
        this.loginService = loginService;
        this.registrationService = registrationService;
    }

    @GetMapping
    @Operation(summary = "Get custom user information")
    public ResponseEntity<CustomUserInfoDTO> getOauth2LoginInfo(@AuthenticationPrincipal OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return new ResponseEntity<>(loginService.login(oAuth2AuthenticationToken.getPrincipal().getAttributes(),
                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId()),
                HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new custom user")
    public ResponseEntity<UserDTO> registerCustomUser(@AuthenticationPrincipal OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                                      @RequestBody @Valid CustomUserInfoDTO dto) {
        loginService.validateRegistrationToken(oAuth2AuthenticationToken, dto);
        return new ResponseEntity<>(registrationService.registerUser(dto), HttpStatus.OK);
    }
}
