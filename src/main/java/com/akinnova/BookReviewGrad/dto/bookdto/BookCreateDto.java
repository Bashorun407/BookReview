package com.akinnova.BookReviewGrad.dto.bookdto;

import lombok.Data;

@Data
public class BookCreateDto {
    private String coverImage;
    private String title;
    private String about;
    private String author;
}
