//package com.akinnova.BookReviewGrad.dto.userdto;
//
//import com.akinnova.BookReviewGrad.entity.UserEntity;
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
//    public ServiceProviderUpdateDto(UserEntity userEntity){
//        //this.username = userEntity.getUsername();
//        this.userType = userEntity.getUserType();
//        this.specialization = userEntity.getSpecialization();
//        this.description = userEntity.getDescription();
//        this.chargePerHour = userEntity.getChargePerHour();
//        this.serviceProviderApplicationStatus = userEntity.getServiceProviderApplicationStatus();
//        this.activeStatus = true;
//    }
//
//    public ServiceProviderUpdateDto(){}
//}
