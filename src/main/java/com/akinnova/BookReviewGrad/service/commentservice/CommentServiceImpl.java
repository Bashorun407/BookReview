package com.akinnova.BookReviewGrad.service.commentservice;
import com.akinnova.BookReviewGrad.dto.commentdto.CommentDeleteDto;
import com.akinnova.BookReviewGrad.dto.commentdto.CommentDto;
import com.akinnova.BookReviewGrad.dto.commentdto.CommentResponseDto;
import com.akinnova.BookReviewGrad.entity.Comment;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.CommentRepository;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService{

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public ResponseEntity<?> addComment(CommentDto commentDto) {
        //Comment and save simultaneously
        commentRepository.save(Comment.builder()
                        .comment(commentDto.getComment())
                        .username(commentDto.getUsername())
                        .commentTime(LocalDateTime.now())
                        .build());
        return ResponseEntity.ok("Done");
    }

    @Override
    public ResponseEntity<?> allComments(int pageNum, int pageSize) {

        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        commentList.stream().skip(pageNum).limit(pageSize).map(
                comment -> CommentResponseDto.builder()
                        .comment(comment.getComment())
                        .commentTime(comment.getCommentTime())
                        .build()
        ).forEach(responseDtoList::add);

        ResponsePojo<List<CommentResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "All comments", responseDtoList, pageNum, pageSize, responseDtoList.size());

        return ResponseEntity.ok(responsePojo);
    }

    @Override
    public ResponseEntity<?> findCommentByTitle(String title, int pageNum, int pageSize) {
        List<Comment> commentList = commentRepository.findByTitle(title)
                .orElseThrow(()-> new ApiException(String.format("There are no comments by title: %s yet", title)));

        //Response Dto list
        List<CommentResponseDto> respondDtoList = new ArrayList<>();

        //Preparing response dto
        commentList.stream().skip(pageNum).limit(pageSize).map(
                comment -> CommentResponseDto.builder()
                        .comment(comment.getComment())
                        .commentTime(comment.getCommentTime())
                        .build()
        ).forEach(respondDtoList::add);

        ResponsePojo<List<CommentResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "Comments by title", respondDtoList, pageNum, pageSize, respondDtoList.size());

        return ResponseEntity.ok(responsePojo);
    }

    @Override
    public ResponseEntity<?> findCommentByUsername(String username, int pageNum, int pageSize) {
        List<Comment> commentList = commentRepository.findByUsername(username)
                .orElseThrow(()-> new ApiException(String.format("There are no comments by username: %s yet", username)));

        //Response Dto list
        List<CommentResponseDto> respondDtoList = new ArrayList<>();

        //Preparing response dto
        commentList.stream().skip(pageNum).limit(pageSize).map(
                comment -> CommentResponseDto.builder()
                        .comment(comment.getComment())
                        .commentTime(comment.getCommentTime())
                        .build()
        ).forEach(respondDtoList::add);

        ResponsePojo<List<CommentResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "Comments by username", respondDtoList, pageNum, pageSize, respondDtoList.size());

        return ResponseEntity.ok(responsePojo);
    }

    @Override
    public ResponseEntity<?> deleteComment(CommentDeleteDto commentDeleteDto) {

        //Find comments by username
        List<Comment> commentList = commentRepository.findByUsername(commentDeleteDto.getUsername())
                .orElseThrow(()-> new ApiException(String.format("Comments by username: %s not available", commentDeleteDto.getUsername())));

        //to remove a comment by user at a specific time
        commentList.stream().filter(x -> x.getTitle().equals(commentDeleteDto.getProjectId()))
                .peek(comment -> {
                    if (comment.getCommentTime().isEqual(commentDeleteDto.getDateTime()))
                        commentRepository.delete(comment);
                });

        return ResponseEntity.ok("Comment deleted successfully");
    }
}
