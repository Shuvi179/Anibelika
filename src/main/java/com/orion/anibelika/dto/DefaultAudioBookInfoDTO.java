package com.orion.anibelika.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DefaultAudioBookInfoDTO {
    private Long id;
    @NotNull
    @Size(min = 1)
    private String name;
    @NotNull
    @Size(min = 1)
    private String description;
    private byte[] image;
    private Long tome;
    private Boolean createdByCurrentUser;
}
