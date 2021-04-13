package com.orion.anibelika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullFilterDTO {
    List<String> genres;
    List<String> authors;
}
