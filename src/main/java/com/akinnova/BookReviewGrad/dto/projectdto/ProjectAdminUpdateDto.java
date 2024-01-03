package com.akinnova.BookReviewGrad.dto.projectdto;

import com.akinnova.BookReviewGrad.enums.ProjectLevelCompletionApproval;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ProjectAdminUpdateDto {
   // private String projectId;
   @Enumerated(EnumType.STRING)
    private ProjectLevelCompletionApproval projectLevelCompletionApproval;

    public ProjectAdminUpdateDto(ProjectLevelCompletionApproval projectLevelCompletionApproval) {
        //this.projectId = projectId;
        this.projectLevelCompletionApproval = projectLevelCompletionApproval;
    }

    public ProjectAdminUpdateDto(){}
}
