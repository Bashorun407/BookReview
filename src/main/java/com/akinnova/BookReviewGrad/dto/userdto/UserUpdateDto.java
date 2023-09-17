package com.akinnova.BookReviewGrad.dto.userdto;

import com.akinnova.BookReviewGrad.entity.UserEntity;
import com.akinnova.BookReviewGrad.enums.UserRole;
import lombok.Data;

@Data
public class UserUpdateDto {
    private String username;
    private String profilePicture;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;
    private String password;

    public UserUpdateDto(UserEntity user){
        this.username = user.getUsername();
        this.profilePicture = user.getProfilePicture();
        this.dateOfBirth = user.getDateOfBirth();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public UserUpdateDto(){}
}
