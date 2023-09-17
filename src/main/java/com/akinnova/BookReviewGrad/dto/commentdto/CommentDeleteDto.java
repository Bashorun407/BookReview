package com.akinnova.BookReviewGrad.dto.commentdto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDeleteDto {
    private String username;
    private LocalDateTime dateTime;

    public CommentDeleteDto(String username, LocalDateTime dateTime) {
        this.username = username;
        this.dateTime = dateTime;
    }

    public CommentDeleteDto(){}
}
