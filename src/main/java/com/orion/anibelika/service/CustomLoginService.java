package com.orion.anibelika.service;

import com.orion.anibelika.dto.CustomUserInfoDTO;

import java.util.Map;

public interface CustomLoginService {
    CustomUserInfoDTO login(Map<String, Object> attributes, String clientId);
}
