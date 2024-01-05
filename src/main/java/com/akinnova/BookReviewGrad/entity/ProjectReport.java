package com.akinnova.BookReviewGrad.entity;

import com.akinnova.BookReviewGrad.enums.JobAcceptanceStatus;
import com.akinnova.BookReviewGrad.enums.ProjectCompletionStatus;
import com.akinnova.BookReviewGrad.enums.ProjectLevelCompletionApproval;
import com.akinnova.BookReviewGrad.enums.ProjectStartApproval;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class ProjectReport extends BaseInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;
    @Enumerated(EnumType.STRING)
    private JobAcceptanceStatus jobAcceptanceStatus;
    @Enumerated(EnumType.STRING)
    private ProjectStartApproval projectStartApproval;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;
    @Enumerated(EnumType.STRING)
    private ProjectLevelCompletionApproval projectLevelCompletionApproval;
    @Enumerated(EnumType.STRING)
    private ProjectCompletionStatus projectCompletionStatus;
    private Boolean activeStatus;
//    @ManyToMany
//    @JoinTable(name = "project_report_user",
//            joinColumns = @JoinColumn(name = "project_report_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
//    )
//    private Set<UserEntity> users;

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((ProjectReport) obj).id);
    }

    @Override
    public int hashCode(){
        return 2022;
    }
}
