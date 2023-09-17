package com.akinnova.BookReviewGrad.dto.userdto;

import com.akinnova.BookReviewGrad.enums.ApplicationReviewStatus;
import com.akinnova.BookReviewGrad.enums.UserRole;
import com.akinnova.BookReviewGrad.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AdminUpdateDto {
    //private String username;
    private UserRole userRole;
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private ApplicationReviewStatus reviewStatus;

    public AdminUpdateDto(UserRole userRole, UserType userType, ApplicationReviewStatus reviewStatus) {
        //this.username = username;
        this.userRole = userRole;
        this.userType = userType;
        this.reviewStatus = reviewStatus;
    }

    public AdminUpdateDto() {}
}
