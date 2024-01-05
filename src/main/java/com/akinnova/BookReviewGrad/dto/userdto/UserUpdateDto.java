package com.akinnova.BookReviewGrad.dto.userdto;

import com.akinnova.BookReviewGrad.entity.UserEntity;
import lombok.Data;

@Data
public class UserUpdateDto {
    private String username;
    private String profilePicture;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;
    private String password;

    public UserUpdateDto(UserEntity userEntity){
        this.username = userEntity.getUsername();
        this.profilePicture = userEntity.getProfilePicture();
        this.dateOfBirth = userEntity.getDateOfBirth();
        this.phoneNumber = userEntity.getPhoneNumber();
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
    }

    public UserUpdateDto(){}
}
