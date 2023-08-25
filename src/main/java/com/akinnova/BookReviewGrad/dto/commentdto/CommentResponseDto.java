package com.akinnova.BookReviewGrad.dto.commentdto;

import com.akinnova.BookReviewGrad.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private String comment;
    private LocalDateTime commentTime;

    public CommentResponseDto(Comment comment) {
        this.comment = comment.getComment();
        this.commentTime = comment.getCommentTime();
    }
}
