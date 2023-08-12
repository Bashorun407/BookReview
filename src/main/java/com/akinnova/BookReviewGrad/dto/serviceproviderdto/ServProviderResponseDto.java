package com.akinnova.BookReviewGrad.dto.serviceproviderdto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServProviderResponseDto {
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String description;
}
