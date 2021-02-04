package com.orion.anibelika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDTO {
    private String email;
    private String identification;
    private String nickName;
    private String type;
    private String password;
}
