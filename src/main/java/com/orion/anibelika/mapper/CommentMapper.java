package com.orion.anibelika.mapper;

import com.orion.anibelika.dto.CommentDTO;
import com.orion.anibelika.dto.NewCommentDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.BookRatingVote;
import com.orion.anibelika.entity.Comment;
import com.orion.anibelika.entity.DataUser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    private final UserMapper userMapper;

    public CommentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Comment map(NewCommentDTO commentDTO, DataUser user, AudioBook book, BookRatingVote vote) {
        Comment comment = new Comment();
        comment.setBook(book);
        comment.setUser(user);
        comment.setText(commentDTO.getText());
        comment.setCreateTime(new Date());
        comment.setRatingVote(vote);
        return comment;
    }

    public CommentDTO map(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setRating(comment.getRatingVote().getRating());
        dto.setText(comment.getText());
        dto.setCreateTime(comment.getCreateTime());
        dto.setUserInfo(userMapper.mapUnAuthorize(comment.getUser()));
        return dto;
    }

    public List<CommentDTO> mapAll(List<Comment> comments) {
        return comments.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
