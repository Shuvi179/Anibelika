package com.orion.anibelika.dto;

import lombok.Data;

@Data
public class DefaultAudioBookInfoDTO {
    private Long id;
    private String name;
    private String description;
    private byte[] image;
    private Long tome;
    private Boolean createdByCurrentUser;
}
