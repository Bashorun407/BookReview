package com.akinnova.BookReviewGrad.service.commentservice;

import com.akinnova.BookReviewGrad.dto.commentdto.CommentDeleteDto;
import com.akinnova.BookReviewGrad.dto.commentdto.CommentDto;
import org.springframework.http.ResponseEntity;

public interface ICommentService {

    ResponseEntity<?> addComment(CommentDto commentDto);
    ResponseEntity<?> allComments(int pageNum, int pageSize);
    //ResponseEntity<?> findCommentByTitle(String title, int pageNum, int pageSize);
    ResponseEntity<?> findCommentByUsername(String username, int pageNum, int pageSize);
    ResponseEntity<?> deleteComment(CommentDeleteDto commentDeleteDto);
}
