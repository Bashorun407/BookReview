package com.akinnova.BookReviewGrad.dto.userdto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String profilePicture;
    private String phoneNumber;
    private String username;
    private String email;
    private String password;
    private String description;
}
