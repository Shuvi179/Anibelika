package com.orion.anibelika.controller;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import com.orion.anibelika.service.AudioBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@Validated
@RequestMapping(value = "/api/v1/book")
public class AudioBookController {

    private final AudioBookService audioBookService;

    private static final Integer DEFAULT_ELEMENTS_ON_PAGE_NUMBER = 20;

    public AudioBookController(AudioBookService audioBookService) {
        this.audioBookService = audioBookService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DefaultAudioBookInfoDTO> getAudioBookById(@PathVariable @Min(1) Long id) {
        return new ResponseEntity<>(audioBookService.getBookById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<PaginationAudioBookInfoDTO> getAudioBookForPage(@RequestParam @Min(1) Integer pageNumber,
                                                                          @RequestParam Optional<Integer> elementsByPage) {
        return new ResponseEntity<>(audioBookService.getAudioBookPage(pageNumber, elementsByPage.orElse(DEFAULT_ELEMENTS_ON_PAGE_NUMBER)), HttpStatus.OK);
    }

    @PostMapping
    public void addNewAudioBook(@RequestBody @Validated DefaultAudioBookInfoDTO dto) {
        audioBookService.addAudioBook(dto);
    }

    @PutMapping
    public void updateAudioBook(@RequestBody @Validated DefaultAudioBookInfoDTO dto) {
        audioBookService.updateAudioBook(dto);
    }
}
