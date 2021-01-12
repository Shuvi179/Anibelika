package com.orion.anibelika.dto;

import com.orion.anibelika.validation.annotation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {
    private Long id;

    @NotEmpty
    @NotNull
    private String nickName;
    private String fullName;

    @ValidEmail
    private String email;
}
