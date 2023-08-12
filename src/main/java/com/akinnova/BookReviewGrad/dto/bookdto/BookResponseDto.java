package com.akinnova.BookReviewGrad.dto.bookdto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponseDto {
    private String coverImage;
    private String title;
    private String author;
    private String summary;
}
