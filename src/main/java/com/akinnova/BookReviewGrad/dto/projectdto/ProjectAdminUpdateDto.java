package com.akinnova.BookReviewGrad.dto.projectdto;

import com.akinnova.BookReviewGrad.enums.ProjectLevelApproval;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ProjectAdminUpdateDto {
   // private String projectId;
   @Enumerated(EnumType.STRING)
    private ProjectLevelApproval projectLevelApproval;

    public ProjectAdminUpdateDto(ProjectLevelApproval projectLevelApproval) {
        //this.projectId = projectId;
        this.projectLevelApproval = projectLevelApproval;
    }

    public ProjectAdminUpdateDto(){}
}
