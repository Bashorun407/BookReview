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

import static com.akinnova.BookReviewGrad.entity.enums.UserRoleEnum.*;

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
                        .userRoleEnum(userCreateDto.getUserRoleEnum())
                        //.userRole(userCreateDto.getRoleName())
                        .description(userCreateDto.getDescription())
                .build());

        //if user role is "ServiceProvider"...create an object of service provider and saves to database
        if(userCreateDto.getUserRoleEnum() == Service_Provider){
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


        //Save 'role' (i.e. UserRoleEnum type) to Role database if it doesn't already exist...The following if conditions
        //Checks and saves the role name accordingly.
        if(userCreateDto.getUserRoleEnum() == Client && !userRoleRepository.existsByRoleName(Client)){
            userRoleRepository.save(UserRole.builder().roleName(Client).build());
            }

        else if(userCreateDto.getUserRoleEnum() == Service_Provider && !userRoleRepository.existsByRoleName(Service_Provider)){
            userRoleRepository.save(UserRole.builder().roleName(Service_Provider).build());
        }

        else if(userCreateDto.getUserRoleEnum() == ADMIN && !userRoleRepository.existsByRoleName(ADMIN)){
            userRoleRepository.save(UserRole.builder().roleName(ADMIN).build());
        }
        //Response dto
        UserResponseDto responseDto = new UserResponseDto(userEntity);

        return new ResponsePojo<>(ResponseUtils.CREATED, true, "New user added successfully", responseDto);
    }

    @Override
    public ResponseEntity<?> allUsers(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = userRepository.findAll().stream().filter(UserEntity::getActiveStatus).toList();
        List<UserResponseDto> responseDtoList = new ArrayList<>();

        //Response dto
        userEntityList.stream().skip(pageNum - 1).limit(pageSize).map(
                UserResponseDto::new
        ).forEach(responseDtoList::add);

        ResponsePojo<List<UserResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "All Service providers", responseDtoList, pageNum, pageSize, responseDtoList.size());

        return ResponseEntity.ok(responsePojo);
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

        UserResponseDto responseDto = new  UserResponseDto(userEntity);
        return new ResponseEntity<>(responseDto, HttpStatus.FOUND);
    }

    // TODO: 13/08/2023 To implement the following methods
    @Override
    public ResponseEntity<?> FindClients(int pageNum, int pageSize) {
        List<UserEntity> clientList = userRepository.findAll().stream().filter(x-> x.getUserRoleEnum() == Client)
                .toList();
        List<UserResponseDto> responseDtoList = new ArrayList<>();

        if(clientList.isEmpty())
            return new ResponseEntity<>("There are no Clients yet.", HttpStatus.NOT_FOUND);

        clientList.stream().filter(UserEntity::getActiveStatus).skip(pageNum - 1).limit(pageSize)
                .map(
                        UserResponseDto::new
                ).forEach(responseDtoList::add);

        return ResponseEntity.ok()
                .header("User page number: ", String.valueOf(pageNum))
                .header("User page size: ", String.valueOf(pageSize))
                .header("User total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> FindServiceProviders(int pageNum, int pageSize) {
        List<UserEntity> clientList = userRepository.findAll().stream().filter(x-> x.getUserRoleEnum() == Service_Provider)
                .toList();
        List<UserResponseDto> responseDtoList = new ArrayList<>();

        if(clientList.isEmpty())
            return new ResponseEntity<>("There are no Service Providers yet.", HttpStatus.NOT_FOUND);

        clientList.stream().filter(UserEntity::getActiveStatus).skip(pageNum - 1).limit(pageSize)
                .map(
                        UserResponseDto::new
                ).forEach(responseDtoList::add);

        return ResponseEntity.ok()
                .header("User page number: ", String.valueOf(pageNum))
                .header("User page size: ", String.valueOf(pageSize))
                .header("User total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> FindAdmins(int pageNum, int pageSize) {
        List<UserEntity> clientList = userRepository.findAll().stream().filter(x-> x.getUserRoleEnum() == ADMIN)
                .toList();
        List<UserResponseDto> responseDtoList = new ArrayList<>();

        if(clientList.isEmpty())
            return new ResponseEntity<>("There are no Admins yet.", HttpStatus.NOT_FOUND);

        clientList.stream().filter(UserEntity::getActiveStatus).skip(pageNum - 1).limit(pageSize)
                .map(
                        UserResponseDto::new
                ).forEach(responseDtoList::add);

        return ResponseEntity.ok()
                .header("User page number: ", String.valueOf(pageNum))
                .header("User page size: ", String.valueOf(pageSize))
                .header("User total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> updateUser(UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userRepository.findByUsername(userUpdateDto.getUsername()).filter(UserEntity::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format(" There is no user by username: %s ", userUpdateDto.getUsername())));

        userEntity.setProfilePicture(userUpdateDto.getProfilePicture());
        userEntity.setPhoneNumber(userUpdateDto.getPhoneNumber());
        userEntity.setUsername(userUpdateDto.getUsername());
        userEntity.setEmail(userUpdateDto.getEmail());
        userEntity.setPassword(userUpdateDto.getPassword());
        userEntity.setUserRoleEnum(userUpdateDto.getUserRoleEnum());
        userEntity.setDescription(userUpdateDto.getDescription());
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
