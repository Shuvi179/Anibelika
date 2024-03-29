package com.orion.anibelika.service.impl;

import com.orion.anibelika.dto.NewCommentDTO;
import com.orion.anibelika.dto.PaginationCommentDTO;
import com.orion.anibelika.entity.AudioBook;
import com.orion.anibelika.entity.Comment;
import com.orion.anibelika.entity.DataUser;
import com.orion.anibelika.exception.PermissionException;
import com.orion.anibelika.mapper.CommentMapper;
import com.orion.anibelika.repository.CommentRepository;
import com.orion.anibelika.service.AudioBookService;
import com.orion.anibelika.service.CommentService;
import com.orion.anibelika.service.UserHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AudioBookService audioBookService;
    private final UserHelper userHelper;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, AudioBookService audioBookService, UserHelper userHelper,
                              CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.audioBookService = audioBookService;
        this.userHelper = userHelper;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional
    public PaginationCommentDTO getCommentPageByBook(Long bookId, int pageNumber, int numberByPage) {
        AudioBook audioBook = audioBookService.getBookEntityById(bookId);
        Pageable request = PageRequest.of(pageNumber - 1, numberByPage, Sort.Direction.DESC, "createTime");
        Page<Comment> comments = commentRepository.findAllByBook(audioBook, request);
        return new PaginationCommentDTO(commentMapper.mapAll(comments.getContent()), comments.getTotalPages(), comments.getTotalElements());
    }

    @Override
    public void addNewComment(NewCommentDTO commentDTO, Long bookId) {
        if (!userHelper.isCurrentUserAuthenticated()) {
            throw new PermissionException("No permission for this data");
        }
        DataUser currentUser = userHelper.getCurrentDataUser();
        AudioBook audioBook = audioBookService.getBookEntityById(bookId);
        Comment comment = commentMapper.map(commentDTO, currentUser, audioBook);
        commentRepository.save(comment);
    }
}
