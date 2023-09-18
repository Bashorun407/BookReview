package com.akinnova.BookReviewGrad.dto.userdto;

import lombok.Data;
import org.apache.catalina.User;

@Data
public class UserCreateDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    public UserCreateDto(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserCreateDto(){};
}
