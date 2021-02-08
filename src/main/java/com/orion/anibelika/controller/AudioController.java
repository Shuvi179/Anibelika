package com.orion.anibelika.controller;

import com.orion.anibelika.dto.AudioDTO;
import com.orion.anibelika.service.BaseAudioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping(value = "/api/v1/book/{bookId}/baseAudio")
public class AudioController {

    private final BaseAudioService baseAudioService;

    public AudioController(BaseAudioService baseAudioService) {
        this.baseAudioService = baseAudioService;
    }

    @PostMapping
    @Operation(summary = "Add new audio placeholder by book")
    public ResponseEntity<Long> addNewAudio(@RequestBody @Valid AudioDTO audioDTO, @PathVariable @Min(1) Long bookId) {
        return new ResponseEntity<>(baseAudioService.addNewAudio(audioDTO, bookId), HttpStatus.OK);
    }
}
