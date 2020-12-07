package com.orion.anibelika.controller;

import com.orion.anibelika.dto.DefaultAudioBookInfoDTO;
import com.orion.anibelika.dto.ParseDTO;
import com.orion.anibelika.parser.ParseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<DefaultAudioBookInfoDTO> parseBookByUrl(@RequestBody @Validated ParseDTO dto) {
        return new ResponseEntity<>(parseService.parseBookByUrl(dto.getUrl()), HttpStatus.OK);
    }
}
