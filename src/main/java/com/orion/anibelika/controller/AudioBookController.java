package com.orion.anibelika.controller;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import com.orion.anibelika.service.AudioBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/book")
public class AudioBookController {

    private final AudioBookService audioBookService;

    private final static Integer DEFAULT_ELEMENTS_ON_PAGE_NUMBER = 20;

    public AudioBookController(AudioBookService audioBookService) {
        this.audioBookService = audioBookService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DefaultAudioBookInfoDTO> getAudioBookById(@PathVariable Long id) {
        return new ResponseEntity<>(audioBookService.getBookById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/page")
    @PreAuthorize("#pageNumber != null && #pageNumber > 0")
    public ResponseEntity<PaginationAudioBookInfoDTO> getAudioBookForPage(@RequestParam Integer pageNumber,
                                                                          @RequestParam Optional<Integer> elementsByPage) {
        return new ResponseEntity<>(audioBookService.getAudioBookPage(pageNumber, elementsByPage.orElse(DEFAULT_ELEMENTS_ON_PAGE_NUMBER)), HttpStatus.OK);
    }

    @PutMapping
    public void addNewAudioBook(@RequestBody DefaultAudioBookInfoDTO dto) {
        audioBookService.addAudioBook(dto);
    }

    @PostMapping
    public void updateAudioBook(@RequestBody DefaultAudioBookInfoDTO dto) {
        audioBookService.updateAudioBook(dto);
    }
}
