package com.orion.anibelika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginationAudioBookInfoDTO {
    private List<DefaultAudioBookInfoDTO> audioBooks;
}
