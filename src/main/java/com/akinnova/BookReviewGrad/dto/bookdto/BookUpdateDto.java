package com.akinnova.BookReviewGrad.dto.bookdto;

import lombok.Data;

@Data
public class BookUpdateDto {

    private String coverImage;
    private String title;
    private String author;
    private String ISBN;
    private String genre;
    private String summary;
    private Boolean activeStatus;
}
