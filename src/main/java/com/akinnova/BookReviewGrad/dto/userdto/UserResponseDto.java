package com.akinnova.BookReviewGrad.dto.userdto;

import com.akinnova.BookReviewGrad.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
public class UserResponseDto {
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String description;

    public UserResponseDto(UserEntity userEntity){
        this.description = userEntity.getDescription();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.profilePicture = userEntity.getProfilePicture();
    }
}
