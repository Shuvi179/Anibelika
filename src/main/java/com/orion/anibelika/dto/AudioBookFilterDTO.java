package com.orion.anibelika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AudioBookFilterDTO {
    private String bookName;
    private String authorNickName;
    private Long sortBy;
    private List<String> genres;
}
