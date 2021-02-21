package com.orion.anibelika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonateRequisiteDTO {
    private Long id;
    @NotEmpty
    @NotNull
    private String name;
    @NotEmpty
    @NotEmpty
    private String value;
}
