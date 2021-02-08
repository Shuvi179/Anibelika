package com.orion.anibelika.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AudioDTO {
    @NotNull
    @NotEmpty
    private String name;
}
