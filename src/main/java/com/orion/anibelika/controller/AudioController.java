package com.orion.anibelika.controller;

import com.orion.anibelika.dto.AudioDTO;
import com.orion.anibelika.service.BaseAudioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping(value = "/api/v1/baseAudio")
public class AudioController {

    private final BaseAudioService baseAudioService;

    public AudioController(BaseAudioService baseAudioService) {
        this.baseAudioService = baseAudioService;
    }

    @PostMapping
    public ResponseEntity<Long> addNewAudio(@RequestBody @Valid AudioDTO audioDTO) {
        return new ResponseEntity<>(baseAudioService.addNewAudio(audioDTO), HttpStatus.OK);
    }
}
