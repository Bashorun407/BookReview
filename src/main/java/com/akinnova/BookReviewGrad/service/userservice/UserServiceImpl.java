package com.akinnova.BookReviewGrad.service.userservice;

import com.akinnova.BookReviewGrad.dto.userdto.*;
import com.akinnova.BookReviewGrad.email.emaildto.EmailDetail;
import com.akinnova.BookReviewGrad.email.emailservice.EmailServiceImpl;
import com.akinnova.BookReviewGrad.entity.UserEntity;
import com.akinnova.BookReviewGrad.enums.ResponseType;
import com.akinnova.BookReviewGrad.enums.ServiceProviderSpecialization;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import com.akinnova.BookReviewGrad.response.EmailResponse;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import com.akinnova.BookReviewGrad.utilities.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.akinnova.BookReviewGrad.enums.ApplicationStatus.NOT_SENT;
import static com.akinnova.BookReviewGrad.enums.ApplicationReviewStatus.NOT_CONFIRMED;
import static com.akinnova.BookReviewGrad.enums.UserRole.*;
import static com.akinnova.BookReviewGrad.enums.UserType.CLIENT;
import static com.akinnova.BookReviewGrad.enums.UserType.SERVICE_PROVIDER;

@Service
public class UserServiceImpl implements IUserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public ResponsePojo<UserResponseDto> addUser(UserCreateDto userCreateDto) {
        boolean check = userRepository.existsByUsername(userCreateDto.getUsername());

        if(check)
            throw new ApiException(String.format(ResponseUtils.USER_EXISTS, userCreateDto.getUsername()));

        //Add and save new client to repository
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .userId(Utility.generateUniqueIdentifier(10, userCreateDto.getUsername()))
                .username(userCreateDto.getUsername())
                .email(userCreateDto.getEmail())
                // TODO: 04/09/2023 password encoder should be used here
                //.password(userCreateDto.getPassword())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .userRole(REGULAR_USER)

                .userType(CLIENT)
                .specialization(ServiceProviderSpecialization.NONE)
                .applicationStatus(NOT_SENT)
                .reviewStatus(NOT_CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        logger.info("A new user has been added.");
        // TODO: 04/09/2023 Email should be sent to users after registering.

        //Sending email to the project owner that a new project has been created.
        EmailDetail emailDetail = EmailDetail.builder()
                .recipient(userEntity.getEmail())
                .subject(EmailResponse.USER_CREATION_SUBJECT)
                .body(String.format(EmailResponse.USER_CREATION_MAIL, userEntity.getLastName(), userEntity.getUserId()))
                .build();

        emailService.sendSimpleEmail(emailDetail);

        return new ResponsePojo<>(ResponseType.SUCCESS, ResponseUtils.USER_ADDED, new UserResponseDto(userEntity));
    }

    @Override
    public ResponseEntity<?> allUsers(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream().filter(UserEntity::getActiveStatus).toList();

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All users", userEntityList.stream()
                .sorted(Comparator.comparing(UserEntity::getLastName).thenComparing(UserEntity::getFirstName))
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new).collect(Collectors.toList())));
    }

//    @Override
//    public ResponseEntity<?> SearchUser(String username, String phoneNumber, String email) {
//
//        UserEntity userEntity = new UserEntity();
//
//        if(StringUtils.hasText(username))
//            userEntity = userRepository.findByUsername(username).filter(UserEntity::getActiveStatus)
//                    .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, username)));
//
//        if(StringUtils.hasText(email))
//            userEntity = userRepository.findByEmail(username).filter(UserEntity::getActiveStatus)
//                    .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, email)));
//
//        return new ResponseEntity<>(new UserResponseDto(userEntity), HttpStatus.FOUND);
//    }

    // TODO: 13/08/2023 To implement the following methods
    @Override
    public ResponseEntity<?> FindClients(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream()
                .filter(x-> x.getUserType() == CLIENT)
                .filter(UserEntity::getActiveStatus)
                .sorted(Comparator.comparing(UserEntity::getLastName).thenComparing(UserEntity::getFirstName))
                .toList();

        if(userEntityList.isEmpty())
            return new ResponseEntity<>(ResponseUtils.NO_CLIENT_YET, HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All clients", userEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new).collect(Collectors.toList())));
    }

    @Override
    public ResponseEntity<?> FindServiceProviders(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream()
                .filter(x-> x.getUserType() == SERVICE_PROVIDER)
                .filter(UserEntity::getActiveStatus)
                .sorted(Comparator.comparing(UserEntity::getLastName).thenComparing(UserEntity::getFirstName))
                .sorted(Comparator.comparingDouble(UserEntity::getChargePerHour))
                .toList();

        if(userEntityList.isEmpty())
            return new ResponseEntity<>(ResponseUtils.NO_SERVICE_PROVIDER_YET, HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All service providers", userEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new).collect(Collectors.toList())));

    }

    @Override
    public ResponseEntity<?> FindRegularUsers(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream()
                .filter(x-> x.getUserRole() == REGULAR_USER)
                .filter(UserEntity::getActiveStatus)
                .sorted(Comparator.comparing(UserEntity::getLastName).thenComparing(UserEntity::getFirstName))
                .toList();

        if(userEntityList.isEmpty())
            return new ResponseEntity<>(ResponseUtils.NO_REGULAR_USER_YET, HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All regular users", userEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new).collect(Collectors.toList())));
    }

    @Override
    public ResponseEntity<?> FindAdmins(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream()
                .filter(x-> x.getUserRole() == ADMIN)
                .filter(UserEntity::getActiveStatus)
                .sorted(Comparator.comparing(UserEntity::getLastName).thenComparing(UserEntity::getFirstName))
                .toList();

        if(userEntityList.isEmpty())
            return new ResponseEntity<>(ResponseUtils.NO_ADMIN_YET, HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All admins", userEntityList.stream()
                .skip(pageNum - 1).limit(pageSize).map(UserResponseDto::new).collect(Collectors.toList())));

    }


    @Override
    public ResponseEntity<?> updateUser(UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userRepository.findByUsername(userUpdateDto.getUsername())
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, userUpdateDto.getUsername())));

        userEntity.setProfilePicture(userUpdateDto.getProfilePicture());
        userEntity.setDateOfBirth(userUpdateDto.getDateOfBirth());
        userEntity.setPhoneNumber(userUpdateDto.getPhoneNumber());
        userEntity.setEmail(userUpdateDto.getEmail());
        userEntity.setPassword(userUpdateDto.getPassword());
        userEntity.setActiveStatus(true);
        userEntity.setModifiedOn(LocalDateTime.now());

        //Save to repository
        userRepository.save(userEntity);
        logger.info("User details has been modified.");

        // TODO: 04/09/2023 Email should be sent to user to notify for update
        //Sending email to the project owner that a new project has been created.
        EmailDetail emailDetail = EmailDetail.builder()
                .recipient(userEntity.getEmail())
                .subject(EmailResponse.USER_UPDATE_NOTIFICATION)
                .body(String.format(EmailResponse.USER_UPDATE_MAIL, userEntity.getLastName()))
                .build();

        emailService.sendSimpleEmail(emailDetail);

        return ResponseEntity.ok(ResponseUtils.USER_UPDATE_MESSAGE);
    }

    @Override
    public ResponseEntity<?> serviceProviderUpdate(String username, ServiceProviderUpdateDto providerUpdateDto) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, username)));

        userEntity.setUserType(providerUpdateDto.getUserType());
        userEntity.setSpecialization(providerUpdateDto.getSpecialization());
        userEntity.setDescription(providerUpdateDto.getDescription());
        userEntity.setChargePerHour(providerUpdateDto.getChargePerHour());
        userEntity.setApplicationStatus(providerUpdateDto.getApplicationStatus());

        //Save update to database
        userRepository.save(userEntity);
        logger.info("Service provider details has been updated.");

        //Sending email to the project owner that a new project has been created.
        EmailDetail emailDetail = EmailDetail.builder()
                .recipient(userEntity.getEmail())
                .subject(EmailResponse.USER_UPDATE_NOTIFICATION)
                .body(String.format(EmailResponse.USER_UPDATE_MAIL, userEntity.getLastName()))
                .build();

        emailService.sendSimpleEmail(emailDetail);

        return ResponseEntity.ok(ResponseUtils.USER_UPDATE_MESSAGE);
    }

    @Override
    public ResponseEntity<?> jobRoleUpdate(String username, AdminUpdateDto adminUpdateDto) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, username)));

        userEntity.setUserRole(adminUpdateDto.getUserRole());
        userEntity.setUserType(adminUpdateDto.getUserType());
        userEntity.setReviewStatus(adminUpdateDto.getReviewStatus());

        //Save to repository
        userRepository.save(userEntity);
        logger.info("Admin has updated user details.");
        return ResponseEntity.ok(ResponseUtils.USER_UPDATE_MESSAGE);

    }

    @Override
    public ResponseEntity<?> deleteUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).filter(UserEntity::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, username)));

        //Only resets active status to 'false'
        userEntity.setActiveStatus(false);
        //Save to database
        userRepository.save(userEntity);
        logger.info("User details has been deleted...soft delete.");

        return ResponseEntity.ok(ResponseUtils.USER_DELETE_MESSAGE);
    }
}
