package com.orion.anibelika.controller;

import com.orion.anibelika.dto.VkTokenDTO;
import com.orion.anibelika.service.VKLoginService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/vk")
public class VkController {

    private final VKLoginService vkLoginService;

    public VkController(VKLoginService vkLoginService) {
        this.vkLoginService = vkLoginService;
    }

    @GetMapping
    public RedirectView vkLogin() {
        return vkLoginService.authorize();
    }

    @GetMapping("/redirect")
    public void redirectVk(@RequestParam String code) {
        vkLoginService.accessToken(code);
    }

    @PostMapping("/redirect")
    public RedirectView getVkUserInfo(@RequestBody VkTokenDTO dto) {
        return vkLoginService.redirectToLogin(dto);
    }
}
