package com.orion.anibelika.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ParseDTO {
    @NotNull
    @NotEmpty
    //TODO add pattern shiki validation
    private String url;
}
