package com.akinnova.BookReviewGrad.controller;

import com.akinnova.BookReviewGrad.dto.commentdto.CommentDeleteDto;
import com.akinnova.BookReviewGrad.dto.commentdto.CommentDto;
import com.akinnova.BookReviewGrad.service.commentservice.CommentServiceImpl;
import com.akinnova.BookReviewGrad.service.commentservice.ICommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final ICommentService commentService;
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
        return commentService.addComment(commentDto);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findCommentByUsername(@PathVariable String username,
                                                   @RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return commentService.findCommentByUsername(username, pageNum, pageSize);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestBody CommentDeleteDto commentDeleteDto) {
        return commentService.deleteComment(commentDeleteDto);
    }
}
