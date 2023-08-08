package com.akinnova.BookReviewGrad.dto.userdto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private String profilePicture;
    private String firstName;
    private String lastName;
}
