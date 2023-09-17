package com.akinnova.BookReviewGrad.dto.projectdto;

import com.akinnova.BookReviewGrad.entity.Project;

import lombok.Data;

@Data
//@Builder
public class ProjectResponseDto {
    private String coverImage;
    private String title;
    private String clientUsername;
    private String projectId;

    public ProjectResponseDto(Project bookEntity){
        this.coverImage = bookEntity.getCoverImage();
        this.title = bookEntity.getTitle();
        this.clientUsername = bookEntity.getClientUsername();
        this.projectId = bookEntity.getProjectId();

    }
}
