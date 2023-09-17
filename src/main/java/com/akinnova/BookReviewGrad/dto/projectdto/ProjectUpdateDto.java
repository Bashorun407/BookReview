package com.akinnova.BookReviewGrad.dto.projectdto;

import lombok.Data;

@Data

public class ProjectUpdateDto {
    private String coverImage;
    private String title;
    private String content;
    //private String clientUsername;
    //private String projectId;
    private Boolean activeStatus;

    public ProjectUpdateDto(String coverImage, String title, String content, Boolean activeStatus) {
        this.coverImage = coverImage;
        this.title = title;
        this.content = content;
        //this.clientUsername = clientUsername;
        //this.projectId = projectId;
        this.activeStatus = activeStatus;
    }

    public ProjectUpdateDto(){}
}
