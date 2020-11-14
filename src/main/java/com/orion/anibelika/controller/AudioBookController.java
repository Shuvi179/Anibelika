package com.orion.anibelika.controller;


import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.service.AudioBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/book")
public class AudioBookController {

    private final AudioBookService audioBookService;

    public AudioBookController(AudioBookService audioBookService) {
        this.audioBookService = audioBookService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DefaultAudioBookInfoDTO> getAudioBookById(@PathVariable Optional<Long> id) {
        return new ResponseEntity<>(audioBookService.getBookById(id.orElseThrow(() -> new IllegalArgumentException("Book id is null"))), HttpStatus.OK);
    }

    @PutMapping
    public void addNewAudioBook(@RequestBody DefaultAudioBookInfoDTO dto) {
        audioBookService.addAudioBook(dto);
    }
}
