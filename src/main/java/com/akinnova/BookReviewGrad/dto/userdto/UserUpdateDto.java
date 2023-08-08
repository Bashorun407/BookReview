package com.akinnova.BookReviewGrad.dto.userdto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String username;
    private String email;
    private String password;
    private String profilePicture;
}
