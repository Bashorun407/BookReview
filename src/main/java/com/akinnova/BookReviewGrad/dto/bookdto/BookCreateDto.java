package com.akinnova.BookReviewGrad.dto.bookdto;

import lombok.Data;

@Data
public class BookCreateDto {
    private String coverImage;
    private String title;
    private StringBuffer content;
    private String author;
}
