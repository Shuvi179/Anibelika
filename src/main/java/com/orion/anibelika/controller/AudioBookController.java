package com.orion.anibelika.controller;


import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.service.AudioBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/book")
public class AudioBookController {

    private final AudioBookService audioBookService;

    public AudioBookController(AudioBookService audioBookService) {
        this.audioBookService = audioBookService;
    }

    @GetMapping(value = "/{id}")
    public DefaultAudioBookInfoDTO getAudioBookById(@PathVariable Optional<Long> id) {
        return audioBookService.getBookById(id.orElseThrow(() -> new IllegalArgumentException("Book id is null")));
    }
}
