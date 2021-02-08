package com.orion.anibelika.controller;

import com.orion.anibelika.dto.NewCommentDTO;
import com.orion.anibelika.dto.PaginationCommentDTO;
import com.orion.anibelika.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/book/{bookId}/comment")
public class CommentController {

    private static final int NUMBER_OF_COMMENTS_BY_PAGE = 20;

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/page")
    public ResponseEntity<PaginationCommentDTO> getPaginationCommentByBook(@PathVariable @Min(1) Long bookId,
                                                                           @RequestParam @Min(1) int pageNumber) {
        return new ResponseEntity<>(commentService.getCommentPageByBook(bookId, pageNumber, NUMBER_OF_COMMENTS_BY_PAGE), HttpStatus.OK);
    }

    @PostMapping
    public void addNewComment(@PathVariable @Min(1) Long bookId, @RequestBody NewCommentDTO commentDTO) {
        commentService.addNewComment(commentDTO, bookId);
    }
}
