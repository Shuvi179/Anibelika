package com.orion.anibelika.controller;

import com.orion.anibelika.service.VKLoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
