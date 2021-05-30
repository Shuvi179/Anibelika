package com.orion.anibelika.controller;

import com.orion.anibelika.dto.*;
import com.orion.anibelika.service.AudioBookService;
import com.orion.anibelika.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/api/v1/book")
public class AudioBookController {

    private final AudioBookService audioBookService;
    private final GenreService genreService;

    public static final Integer DEFAULT_ELEMENTS_ON_PAGE_NUMBER = 12;

    public AudioBookController(AudioBookService audioBookService, GenreService genreService) {
        this.audioBookService = audioBookService;
        this.genreService = genreService;

    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get book by id")
    public ResponseEntity<FullAudioBookInfoDTO> getAudioBookById(@PathVariable @Min(1) Long id) {
        return new ResponseEntity<>(audioBookService.getBookById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/page/{pageId}")
    @Operation(summary = "Get page of books")
    public ResponseEntity<PaginationAudioBookInfoDTO> getAudioBookForPage(@RequestBody AudioBookFilterDTO filterDTO, @PathVariable @Min(1) Integer pageId) {
        return new ResponseEntity<>(audioBookService.getAudioBookPage(filterDTO, pageId, DEFAULT_ELEMENTS_ON_PAGE_NUMBER), HttpStatus.OK);
    }

    @GetMapping(value = "/current/page/{pageId}")
    @Operation(summary = "Get page of books created by current user")
    public ResponseEntity<PaginationAudioBookInfoDTO> getMyBooksByPage(@PathVariable @Min(1) Integer pageId) {
        return new ResponseEntity<>(audioBookService.getMyBooksByPage(pageId, DEFAULT_ELEMENTS_ON_PAGE_NUMBER), HttpStatus.OK);
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

    @GetMapping("/genre")
    @Operation(summary = "Get all genres")
    public List<String> getAllGenres() {
        return genreService.getAllGenresName();
    }

    @GetMapping("/filter")
    @Operation(summary = "Get book filter info")
    public ResponseEntity<FullFilterDTO> getFilter() {
        return new ResponseEntity<>(audioBookService.getFilterDto(), HttpStatus.OK);
    }

    @GetMapping("/my")
    @Operation(summary = "Get favourite and created books count")
    public ResponseEntity<MyBookCountDTO> getMyBookCountInfo() {
        return new ResponseEntity<>(audioBookService.getMyBookCountDto(), HttpStatus.OK);
    }
}
