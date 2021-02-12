package com.orion.anibelika.dto;

import com.orion.anibelika.validation.annotation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class PasswordResetDTO {
    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;
}
