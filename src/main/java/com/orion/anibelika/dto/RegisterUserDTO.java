package com.orion.anibelika.dto;

import com.orion.anibelika.validation.annotation.PasswordMatches;
import com.orion.anibelika.validation.annotation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@PasswordMatches
public class RegisterUserDTO {

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    private String nickName;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;
}
