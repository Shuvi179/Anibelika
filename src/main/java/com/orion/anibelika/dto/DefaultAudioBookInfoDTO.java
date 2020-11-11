package com.orion.anibelika.dto;

import lombok.Data;

@Data
public class DefaultAudioBookInfoDTO {
    private Long id;
    private String name;
    private String description;
    private String photoURL;
    private Long tome;
    private Boolean createdByCurrentUser;
}
