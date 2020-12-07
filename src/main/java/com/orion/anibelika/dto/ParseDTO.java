package com.orion.anibelika.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ParseDTO {
    @NotNull
    @Size(min = 1)
    //TODO add pattern shiki validation
    private String url;
}
