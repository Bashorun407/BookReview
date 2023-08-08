package com.akinnova.BookReviewGrad.dto.userdto;

import lombok.Data;

@Data
public class UserCreateDto {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String username;
    private String email;
    private String password;
    private String profilePicture;
}
