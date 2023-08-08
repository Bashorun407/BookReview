package com.akinnova.BookReviewGrad.dto.bookdto;

import lombok.Data;

@Data
public class BookUpdateDto {
    private String title;
    private String author;
    private String genre;
    private String publicationYear;
    private String description;
    private String coverImage;
}
