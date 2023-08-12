package com.akinnova.BookReviewGrad.dto.commentdto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDeleteDto {
    private String title;
    private String username;
    private LocalDateTime date;
}
