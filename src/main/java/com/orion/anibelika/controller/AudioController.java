package com.orion.anibelika.controller;

import com.orion.anibelika.dto.AudioDTO;
import com.orion.anibelika.dto.BookAudioDTO;
import com.orion.anibelika.service.BaseAudioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

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

    @PostMapping("/list")
    @Operation(summary = "Add new audio list placeholder by book")
    public ResponseEntity<List<Long>> addNewAudioList(@RequestBody @Valid List<AudioDTO> audioDTOs, @PathVariable @Min(1) Long bookId) {
        return new ResponseEntity<>(baseAudioService.addNewAudioList(audioDTOs, bookId), HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Update audio placeholder")
    public ResponseEntity<AudioDTO> updateAudio(@RequestBody @Valid AudioDTO audioDTO) {
        return new ResponseEntity<>(baseAudioService.updateAudio(audioDTO), HttpStatus.OK);
    }

    @PutMapping("/list")
    @Operation(summary = "Update audio list placeholder")
    public ResponseEntity<List<AudioDTO>> updateAudioList(@RequestBody @Valid List<AudioDTO> audioDTOs) {
        return new ResponseEntity<>(baseAudioService.updateAudioList(audioDTOs), HttpStatus.OK);
    }

    @DeleteMapping("/{audioId}")
    @Operation(summary = "Delete audio placeholder")
    public void deleteAudio(@PathVariable Long audioId) {
        baseAudioService.removeAudio(audioId);
    }

    @DeleteMapping("/list")
    @Operation(summary = "Delete audio list placeholder")
    public void deleteAudio(@RequestBody List<Long> audioIds) {
        baseAudioService.removeAudioList(audioIds);
    }

    @GetMapping
    @Operation(summary = "Get audios by book")
    public ResponseEntity<BookAudioDTO> getAudioByBook(@PathVariable @Min(1) Long bookId) {
        return new ResponseEntity<>(baseAudioService.getAudioByBook(bookId), HttpStatus.OK);
    }
}
