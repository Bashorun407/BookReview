package com.akinnova.BookReviewGrad.dto.serviceproviderdto;

import com.akinnova.BookReviewGrad.entity.ServProvider;
import lombok.Data;

@Data
public class ServProviderResponseDto {
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String specialization;
    private String description;

    public ServProviderResponseDto(ServProvider servProvider){

        this.profilePicture = servProvider.getProfilePicture();
        this.firstName = servProvider.getFirstName();
        this.lastName = servProvider.getLastName();
        this.specialization = servProvider.getSpecialization();
        this.description = servProvider.getDescription();
    }
}
