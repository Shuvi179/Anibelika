package com.orion.anibelika.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DefaultAudioBookInfoDTO {
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String description;
    private byte[] image;
    private Long tome;
    private List<String> genres;
    private Boolean createdByCurrentUser;
}
