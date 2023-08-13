package com.akinnova.BookReviewGrad.dto.bookdto;

import lombok.Data;

@Data
public class BookUpdateDto {
    private String coverImage;
    private String title;
    private StringBuffer content;
    private String author;
    private String projectId;
    private Boolean activeStatus;
}
