package com.orion.anibelika.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class NewCommentDTO {
    @NotNull
    @NotEmpty
    private String text;

    @NotNull
    @Min(1)
    @Max(5)
    private Long rating;
}
