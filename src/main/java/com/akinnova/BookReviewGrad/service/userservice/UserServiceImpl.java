package com.akinnova.BookReviewGrad.service.userservice;

import com.akinnova.BookReviewGrad.dto.userdto.UserCreateDto;
import com.akinnova.BookReviewGrad.dto.userdto.UserResponseDto;
import com.akinnova.BookReviewGrad.dto.userdto.UserUpdateDto;
import com.akinnova.BookReviewGrad.entity.ServProvider;
import com.akinnova.BookReviewGrad.entity.UserEntity;
import com.akinnova.BookReviewGrad.entity.UserRole;
import com.akinnova.BookReviewGrad.entity.enums.ApplicationStatus;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.ServProviderRepository;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import com.akinnova.BookReviewGrad.repository.UserRoleRepository;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final ServProviderRepository servProviderRepository;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, ServProviderRepository servProviderRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.servProviderRepository = servProviderRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public ResponsePojo<UserResponseDto> addUser(UserCreateDto userCreateDto) {
        boolean check = userRepository.existsByUsername(userCreateDto.getUsername());

        if(check)
            throw new ApiException(String.format("User with username: %s already exists", userCreateDto.getUsername()));

        //Add and save new client to repository
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                        .profilePicture(userCreateDto.getProfilePicture())
                        .firstName(userCreateDto.getFirstName())
                        .lastName(userCreateDto.getLastName())
                        .userId(ResponseUtils.generateUniqueIdentifier(10, userCreateDto.getUsername()))
                        .dateOfBirth(userCreateDto.getDateOfBirth())
                        .phoneNumber(userCreateDto.getPhoneNumber())
                        .username(userCreateDto.getUsername())
                        .email(userCreateDto.getEmail())
                        .password(userCreateDto.getPassword())
                        .userRole(userCreateDto.getRoleName())
                        .description(userCreateDto.getDescription())
                .build());

        //if user role is "ServiceProvider"...create an object of service provider and saves to database
        if(userCreateDto.getRoleName().startsWith("Ser") || userCreateDto.getRoleName().startsWith("ser")){
            servProviderRepository.save(ServProvider.builder()
                            .profilePicture(userEntity.getProfilePicture())
                            .firstName(userEntity.getFirstName())
                            .lastName(userEntity.getLastName())
                            .providerId(userEntity.getUserId())
                            .username(userEntity.getUsername())
                            .email(userEntity.getEmail())
                            .specialization(userCreateDto.getSpecialization())
                            .description(userEntity.getDescription())
                            .applicationStatus(ApplicationStatus.Received)
                            .activeStatus(true)
                            .createdOn(LocalDateTime.now())
                    .build());
        }


        //Save role to Role database if it doesn't already exist
        if(!userRoleRepository.existsByRoleName(userEntity.getUserRole())){
            userRoleRepository.save(UserRole.builder()
                            .roleName(userEntity.getUserRole())
                    .build());
        }
        //Response dto
        UserResponseDto responseDto = UserResponseDto.builder()
                .profilePicture(userEntity.getProfilePicture())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .description(userEntity.getDescription())
                .build();

        ResponsePojo<UserResponseDto> responsePojo = new ResponsePojo<>();
        responsePojo.setMessage("New user added successfully");
        responsePojo.setData(responseDto);

        return responsePojo;
    }

    @Override
    public ResponseEntity<?> allUsers(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream().filter(UserEntity::getActiveStatus).toList();
        List<UserResponseDto> responseDtoList = new ArrayList<>();

        //Response dto
        userEntityList.stream().skip(pageNum).limit(pageSize).map(
                userEntity -> UserResponseDto.builder()
                        .profilePicture(userEntity.getProfilePicture())
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .description(userEntity.getDescription())
                        .build()
        ).forEach(responseDtoList::add);

        return ResponseEntity.ok()
                .header("User page number: ", String.valueOf(pageNum))
                .header("User page size: ", String.valueOf(pageSize))
                .header("User total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> SearchUser(String username, String phoneNumber, String email) {

        UserEntity userEntity = new UserEntity();

        if(StringUtils.hasText(username))
            userEntity = userRepository.findByUsername(username).filter(UserEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("There is no user by username: %s ", username)));

        if(StringUtils.hasText(phoneNumber))
            userEntity = userRepository.findByPhoneNumber(phoneNumber).filter(UserEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("There is no user by phone number: %s ", phoneNumber)));

        if(StringUtils.hasText(email))
            userEntity = userRepository.findByEmail(username).filter(UserEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("There is no user by username: %s ", email)));

        //Preparing response dto

        UserResponseDto responseDto = UserResponseDto.builder()
                .profilePicture(userEntity.getProfilePicture())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .description(userEntity.getDescription())
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?> updateUser(UserUpdateDto clientUpdateDto) {
        UserEntity userEntity = userRepository.findByUsername(clientUpdateDto.getUsername()).filter(UserEntity::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format(" There is no user by username: %s ", clientUpdateDto.getUsername())));

        userEntity.setProfilePicture(clientUpdateDto.getProfilePicture());
        userEntity.setPhoneNumber(clientUpdateDto.getPhoneNumber());
        userEntity.setUsername(clientUpdateDto.getUsername());
        userEntity.setEmail(clientUpdateDto.getEmail());
        userEntity.setPassword(clientUpdateDto.getPassword());
        userEntity.setDescription(clientUpdateDto.getDescription());
        userEntity.setModifiedOn(LocalDateTime.now());

        //Save to repository
        userRepository.save(userEntity);

        return ResponseEntity.ok("user details has been updated successfully.");
    }

    @Override
    public ResponseEntity<?> deleteUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).filter(UserEntity::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("There is no user by username: %s ", username)));

        //Only resets active status to 'false'
        userEntity.setActiveStatus(false);
        //Save to database
        userRepository.save(userEntity);

        return ResponseEntity.ok("User has been deleted.");
    }
}
