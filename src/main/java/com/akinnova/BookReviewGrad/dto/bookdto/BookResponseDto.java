package com.akinnova.BookReviewGrad.dto.bookdto;

import com.akinnova.BookReviewGrad.entity.BookEntity;

import lombok.Data;

@Data
//@Builder
public class BookResponseDto {
    private String coverImage;
    private String title;
    private String author;
    private String projectId;
    private String about;

//Class constructor
    public BookResponseDto(BookEntity bookEntity){
        this.coverImage = bookEntity.getCoverImage();
        this.title = bookEntity.getTitle();
        this.author = bookEntity.getAuthor();
        this.projectId = bookEntity.getProjectId();
        this.about = bookEntity.getAbout();
    }
}
