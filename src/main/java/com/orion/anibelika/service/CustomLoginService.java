package com.orion.anibelika.service;

import com.orion.anibelika.dto.CustomUserInfoDTO;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Map;

public interface CustomLoginService {
    CustomUserInfoDTO login(Map<String, Object> attributes, String clientId);

    void validateRegistrationToken(OAuth2AuthenticationToken oAuth2AuthenticationToken,
                                   CustomUserInfoDTO dto);
}
