package com.akinnova.BookReviewGrad.dto.serviceproviderdto;

import com.akinnova.BookReviewGrad.enums.ApplicationStatus;
import lombok.Data;

@Data
public class ServProviderUpdateDto {
    private String profilePicture;
    private String username;
    private String email;
    private String password;
    private String specialization;
    private String description;
    private ApplicationStatus applicationStatus;
}
