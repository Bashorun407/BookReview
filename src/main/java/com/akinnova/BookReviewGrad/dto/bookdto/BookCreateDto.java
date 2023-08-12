package com.akinnova.BookReviewGrad.dto.bookdto;

import lombok.Data;

@Data
public class BookCreateDto {
    private String title;
    private String author;
    private String genre;
    private String publicationYear;
    private String ISBN;
    private String summary;
    private String coverImage;
}
