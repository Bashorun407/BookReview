//package com.akinnova.BookReviewGrad.dto.userdto;
//
//import com.akinnova.BookReviewGrad.entity.User;
//import com.akinnova.BookReviewGrad.enums.ServiceProviderApplicationStatus;
//import com.akinnova.BookReviewGrad.enums.ServiceProviderSpecialization;
//import com.akinnova.BookReviewGrad.enums.UserType;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import lombok.Data;
//
//@Data
//public class ServiceProviderUpdateDto {
//    //private String username;
//    private UserType userType;
//    @Enumerated(EnumType.STRING)
//    private ServiceProviderSpecialization specialization;
//    private String description;
//    private Double chargePerHour;
//    @Enumerated(EnumType.STRING)
//    private ServiceProviderApplicationStatus serviceProviderApplicationStatus;
//    private Boolean activeStatus;
//
//    public ServiceProviderUpdateDto(User user){
//        //this.username = user.getUsername();
//        this.userType = user.getUserType();
//        this.specialization = user.getSpecialization();
//        this.description = user.getDescription();
//        this.chargePerHour = user.getChargePerHour();
//        this.serviceProviderApplicationStatus = user.getServiceProviderApplicationStatus();
//        this.activeStatus = true;
//    }
//
//    public ServiceProviderUpdateDto(){}
//}
