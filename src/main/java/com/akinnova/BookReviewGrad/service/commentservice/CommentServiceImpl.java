//package com.akinnova.BookReviewGrad.service.commentservice;
//import com.akinnova.BookReviewGrad.dto.commentdto.CommentDeleteDto;
//import com.akinnova.BookReviewGrad.dto.commentdto.CommentDto;
//import com.akinnova.BookReviewGrad.dto.commentdto.CommentResponseDto;
//import com.akinnova.BookReviewGrad.entity.Comment;
//import com.akinnova.BookReviewGrad.response.ResponseType;
//import com.akinnova.BookReviewGrad.exception.ApiException;
//import com.akinnova.BookReviewGrad.repository.CommentRepository;
//import com.akinnova.BookReviewGrad.response.ResponsePojo;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CommentServiceImpl implements ICommentService{
//    private final CommentRepository commentRepository;
//
//    public CommentServiceImpl(CommentRepository commentRepository) {
//        this.commentRepository = commentRepository;
//    }
//
//    @Override
//    public ResponseEntity<?> addComment(CommentDto commentDto) {
//        //Comment and save simultaneously
//        commentRepository.save(Comment.builder()
//                .comment(commentDto.getComment())
//                .username(commentDto.getUsername())
//                .commentTime(LocalDateTime.now())
//                .build());
//        return ResponseEntity.ok("Done");
//    }
//
//    @Override
//    public ResponseEntity<?> allComments(int pageNum, int pageSize) {
//
//        List<Comment> commentList = commentRepository.findAll();
//
//        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All comments", commentList.stream()
//                .skip(pageNum - 1).limit(pageSize).map(CommentResponseDto::new).collect(Collectors.toList())));
//    }
//
//    @Override
//    public ResponseEntity<?> findCommentByUsername(String username, int pageNum, int pageSize) {
//        List<Comment> commentList = commentRepository.findByUsername(username)
//                .orElseThrow(()-> new ApiException(String.format("There are no comments by username: %s yet", username)));
//
//        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Comments by username",
//                commentList.stream().skip(pageNum - 1).limit(pageSize).map(CommentResponseDto::new).collect(Collectors.toList())));
//    }
//
//    @Override
//    public ResponseEntity<?> deleteComment(CommentDeleteDto commentDeleteDto) {
//
//        //Find comments by username
//        List<Comment> commentList = commentRepository.findByUsername(commentDeleteDto.getUsername())
//                .orElseThrow(()-> new ApiException(String.format("Comments by username: %s not available", commentDeleteDto.getUsername())));
//
//        //to remove a comment by userEntity at a specific time
//        commentList.stream().filter(x -> x.getCommentTime().equals(commentDeleteDto.getDateTime()))
//                .peek(commentRepository::delete);
//
//        return ResponseEntity.ok("Comment deleted successfully");
//    }
//}
