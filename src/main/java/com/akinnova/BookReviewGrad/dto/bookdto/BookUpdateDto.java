package com.akinnova.BookReviewGrad.dto.bookdto;

import com.akinnova.BookReviewGrad.entity.enums.ReviewStatus;
import lombok.Data;

@Data
public class BookUpdateDto {
    private String coverImage;
    private String title;
    private StringBuffer content;
    private String author;
    private String projectId;
    private ReviewStatus reviewStatus;
    private Boolean activeStatus;
}
