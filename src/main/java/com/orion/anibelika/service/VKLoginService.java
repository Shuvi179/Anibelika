package com.orion.anibelika.service;

import com.orion.anibelika.dto.CustomUserInfoDTO;
import com.orion.anibelika.dto.VkTokenDTO;
import org.springframework.web.servlet.view.RedirectView;

public interface VKLoginService {
    RedirectView authorize();

    RedirectView redirectToLogin(VkTokenDTO dto);

    void accessToken(String code);

    CustomUserInfoDTO login(String userId, String token);
}
