package com.akinnova.BookReviewGrad.dto.commentdto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDto {
    private String comment;
    private LocalDateTime commentTime;
}
