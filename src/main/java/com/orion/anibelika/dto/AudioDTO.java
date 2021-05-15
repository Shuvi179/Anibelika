package com.orion.anibelika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AudioDTO {
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
    private Long tomeNumber;
    private Long chapterNumber;
}
