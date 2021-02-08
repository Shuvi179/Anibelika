package com.orion.anibelika.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String text;
    private Date createTime;
    private UserDTO userInfo;
}
