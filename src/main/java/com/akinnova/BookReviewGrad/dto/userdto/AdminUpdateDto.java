package com.akinnova.BookReviewGrad.dto.userdto;

import com.akinnova.BookReviewGrad.enums.ServiceProviderApplicationReviewStatus;
import com.akinnova.BookReviewGrad.enums.RoleName;
import com.akinnova.BookReviewGrad.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AdminUpdateDto {
    //private String username;
    private RoleName roleName;
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private ServiceProviderApplicationReviewStatus reviewStatus;

    public AdminUpdateDto(RoleName roleName, UserType userType, ServiceProviderApplicationReviewStatus reviewStatus) {
        //this.username = username;
        this.roleName = roleName;
        this.userType = userType;
        this.reviewStatus = reviewStatus;
    }

    public AdminUpdateDto() {}
}
