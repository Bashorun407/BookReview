package com.akinnova.BookReviewGrad.dto.commentdto;

import lombok.Data;

@Data
public class CommentDto {
    private String comment;
    private String username;

    public CommentDto(String comment, String username) {
        this.comment = comment;
        this.username = username;
    }

    public CommentDto(){}
}
