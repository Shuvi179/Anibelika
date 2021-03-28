package com.orion.anibelika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class BookAudioDTO {
    Map<Long, List<AudioDTO>> audioByTome;
}
