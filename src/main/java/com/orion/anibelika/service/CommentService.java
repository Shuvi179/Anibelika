package com.orion.anibelika.service;

import com.orion.anibelika.dto.NewCommentDTO;
import com.orion.anibelika.dto.PaginationCommentDTO;

public interface CommentService {
    PaginationCommentDTO getCommentPageByBook(Long bookId, int pageNumber, int numberByPage);

    void addNewComment(NewCommentDTO commentDTO, Long bookId);
}
