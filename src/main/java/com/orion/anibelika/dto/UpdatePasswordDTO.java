package com.orion.anibelika.dto;

import com.orion.anibelika.validation.annotation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@PasswordMatches
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String matchingPassword;
}
