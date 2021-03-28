package com.orion.anibelika.controller;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.FullAudioBookInfoDTO;
import com.orion.anibelika.dto.PaginationAudioBookInfoDTO;
import com.orion.anibelika.service.AudioBookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.io.IOException;

@RestController
@Validated
@RequestMapping(value = "/api/v1/book")
public class AudioBookController {

    private final AudioBookService audioBookService;

    private static final Integer DEFAULT_ELEMENTS_ON_PAGE_NUMBER = 10;

    public AudioBookController(AudioBookService audioBookService) {
        this.audioBookService = audioBookService;
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get book by id")
    public ResponseEntity<FullAudioBookInfoDTO> getAudioBookById(@PathVariable @Min(1) Long id) {
        return new ResponseEntity<>(audioBookService.getBookById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/page/{pageId}")
    @Operation(summary = "Get page of books")
    public ResponseEntity<PaginationAudioBookInfoDTO> getAudioBookForPage(@PathVariable @Min(1) Integer pageId) {
        return new ResponseEntity<>(audioBookService.getAudioBookPage(pageId, DEFAULT_ELEMENTS_ON_PAGE_NUMBER), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new book")
    public void addNewAudioBook(@RequestBody @Validated DefaultAudioBookInfoDTO dto) {
        audioBookService.addAudioBook(dto);
    }

    @PutMapping
    @Operation(summary = "Update book by id")
    public void updateAudioBook(@RequestBody @Validated DefaultAudioBookInfoDTO dto) {
        audioBookService.updateAudioBook(dto);
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "Get image for book by id")
    public byte[] getBookImage(@PathVariable @Min(1) Long id) {
        return audioBookService.getBookImage(id);
    }

    @GetMapping(value = "/{id}/image/small", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "Get small image for book by id")
    public byte[] getSmallBookImage(@PathVariable @Min(1) Long id) {
        return audioBookService.getSmallBookImage(id);
    }

    @PostMapping(value = "/{id}/image")
    @Operation(summary = "Update book image by id")
    public void addBookImage(@PathVariable @Min(1) Long id, @RequestParam("file") MultipartFile file) throws IOException {
        audioBookService.saveBookImage(id, file.getBytes());
    }
}
