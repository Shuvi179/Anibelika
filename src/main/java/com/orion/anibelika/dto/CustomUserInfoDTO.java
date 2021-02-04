package com.orion.anibelika.dto;

import com.orion.anibelika.validation.annotation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@Validated
public class CustomUserInfoDTO {
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;
    @NotNull
    @NotEmpty
    private String identification;
    @NotNull
    @NotEmpty
    private String nickName;
    @NotNull
    @NotEmpty
    private String clientId;
}
