package com.akinnova.BookReviewGrad.dto.projectdto;

import com.akinnova.BookReviewGrad.enums.JobAcceptanceStatus;
import com.akinnova.BookReviewGrad.enums.ProjectCompletionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProjectServiceProviderUpdateDto {
    //private String projectId;
    private String serviceProviderUsername;
    @Enumerated(EnumType.STRING)
    private JobAcceptanceStatus jobAcceptanceStatus;
    @Enumerated(EnumType.STRING)
    private ProjectCompletionStatus projectCompletionStatus;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ProjectServiceProviderUpdateDto(String serviceProviderUsername, JobAcceptanceStatus jobAcceptanceStatus, ProjectCompletionStatus projectCompletionStatus, LocalDateTime startDate, LocalDateTime endDate) {
        //this.projectId = projectId;
        this.serviceProviderUsername = serviceProviderUsername;
        this.jobAcceptanceStatus = jobAcceptanceStatus;
        this.projectCompletionStatus = projectCompletionStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ProjectServiceProviderUpdateDto(){}
}
