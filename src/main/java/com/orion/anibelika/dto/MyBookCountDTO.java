package com.orion.anibelika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyBookCountDTO {
    private Integer numberOfFavouriteBooks;
    private Integer numberOfCreatedBooks;
}
