package com.orion.anibelika.controller;

import com.orion.anibelika.service.AudioBookService;
import com.orion.anibelika.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final UserService userService;
    private final AudioBookService audioBookService;

    public ImageController(UserService userService, AudioBookService audioBookService) {
        this.userService = userService;
        this.audioBookService = audioBookService;
    }

    @GetMapping(value = "/book/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getBookImage(@PathVariable @Min(1) Long id) {
        return audioBookService.getBookImage(id);
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getUserImage(@PathVariable @Min(1) Long id) {
        return userService.getUserImage(id);
    }

    @PostMapping(value = "/user/{id}")
    public void addUserImage(@PathVariable @Min(1) Long id, @RequestParam("file") MultipartFile file) throws IOException {
        userService.saveUserImage(id, file.getBytes());
    }

    @PostMapping(value = "/book/{id}")
    public void addBookImage(@PathVariable @Min(1) Long id, @RequestParam("file") MultipartFile file) throws IOException {
        audioBookService.saveBookImage(id, file.getBytes());
    }
}
