package com.orion.anibelika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FullAudioBookInfoDTO {
    private DefaultAudioBookInfoDTO bookInfo;
    private RatingDTO rating;
    private UserDTO author;
    private Boolean favouriteByCurrentUser;
    private Long selectedAsFavourite;
}
