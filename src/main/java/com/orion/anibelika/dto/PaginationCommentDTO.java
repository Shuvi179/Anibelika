package com.orion.anibelika.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginationCommentDTO {
    private List<CommentDTO> comments;
    private long numberOfPages;
    private long numberOfComments;
}
