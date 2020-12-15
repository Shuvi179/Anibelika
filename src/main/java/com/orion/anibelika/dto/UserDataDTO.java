package com.orion.anibelika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {
    private Long id;
    private String nickName;
    private String fullName;
    private String email;
}
