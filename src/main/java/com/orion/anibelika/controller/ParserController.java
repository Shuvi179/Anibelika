package com.orion.anibelika.controller;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.ParseDTO;
import com.orion.anibelika.parser.ParseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/parser")
public class ParserController {

    private final ParseService parseService;

    public ParserController(ParseService parseService) {
        this.parseService = parseService;
    }

    @PostMapping
    @PreAuthorize("#dto.url != null && #dto.url.length() != 0")
    public ResponseEntity<DefaultAudioBookInfoDTO> parseBookByUrl(@RequestBody ParseDTO dto) {
        return new ResponseEntity<>(parseService.parseBookByUrl(dto.getUrl()), HttpStatus.OK);
    }
}
