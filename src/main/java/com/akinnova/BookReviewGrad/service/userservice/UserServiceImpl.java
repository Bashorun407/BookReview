package com.akinnova.BookReviewGrad.service.userservice;

import com.akinnova.BookReviewGrad.dto.userdto.UserCreateDto;
import com.akinnova.BookReviewGrad.dto.userdto.UserResponseDto;
import com.akinnova.BookReviewGrad.dto.userdto.UserUpdateDto;
import com.akinnova.BookReviewGrad.entity.UserEntity;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository clientRepository;

    public UserServiceImpl(UserRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ResponsePojo<UserResponseDto> addUser(UserCreateDto clientCreateDto) {
        boolean check = clientRepository.existsByUsername(clientCreateDto.getUsername());

        if(check)
            throw new ApiException(String.format("Client with username: %s already exists", clientCreateDto.getUsername()));

        //Add and save new client to repository
        UserEntity userEntity = clientRepository.save(UserEntity.builder()
                        .profilePicture(clientCreateDto.getProfilePicture())
                        .firstName(clientCreateDto.getFirstName())
                        .lastName(clientCreateDto.getLastName())
                        .dateOfBirth(clientCreateDto.getDateOfBirth())
                        .phoneNumber(clientCreateDto.getPhoneNumber())
                        .username(clientCreateDto.getUsername())
                        .email(clientCreateDto.getEmail())
                        .password(clientCreateDto.getPassword())
                        .description(clientCreateDto.getDescription())
                .build());

        //Response dto
        UserResponseDto responseDto = UserResponseDto.builder()
                .profilePicture(userEntity.getProfilePicture())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .description(userEntity.getDescription())
                .build();

        ResponsePojo<UserResponseDto> responsePojo = new ResponsePojo<>();
        responsePojo.setMessage("New client added successfully");
        responsePojo.setData(responseDto);

        return responsePojo;
    }

    @Override
    public ResponseEntity<?> allUsers(int pageNum, int pageSize) {
        List<UserEntity> userEntityList = clientRepository.findAll().stream().filter(UserEntity::getActiveStatus).toList();
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
                .header("Client page number: ", String.valueOf(pageNum))
                .header("Client page size: ", String.valueOf(pageSize))
                .header("Client total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> SearchUser(String username, String phoneNumber, String email) {

        UserEntity userEntity = new UserEntity();

        if(StringUtils.hasText(username))
            userEntity = clientRepository.findByUsername(username).filter(UserEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("Client by username: %s ", username)));

        if(StringUtils.hasText(phoneNumber))
            userEntity = clientRepository.findByPhoneNumber(phoneNumber).filter(UserEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("Client by phone number: %s ", phoneNumber)));

        if(StringUtils.hasText(email))
            userEntity = clientRepository.findByEmail(username).filter(UserEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("Client by username: %s ", email)));

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
        UserEntity userEntity = clientRepository.findByUsername(clientUpdateDto.getUsername()).filter(UserEntity::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("Client by username: %s ", clientUpdateDto.getUsername())));

        userEntity.setProfilePicture(clientUpdateDto.getProfilePicture());
        userEntity.setPhoneNumber(clientUpdateDto.getPhoneNumber());
        userEntity.setUsername(clientUpdateDto.getUsername());
        userEntity.setEmail(clientUpdateDto.getEmail());
        userEntity.setPassword(clientUpdateDto.getPassword());
        userEntity.setDescription(clientUpdateDto.getDescription());

        //Save to repository
        clientRepository.save(userEntity);

        return ResponseEntity.ok("Client details has been updated successfully.");
    }

    @Override
    public ResponseEntity<?> deleteUser(String username) {
        UserEntity userEntity = clientRepository.findByUsername(username).filter(UserEntity::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("Client by username: %s ", username)));

        //Only resets active status to 'false'
        userEntity.setActiveStatus(false);
        //Save to database
        clientRepository.save(userEntity);

        return ResponseEntity.ok("Client has been deleted.");
    }
}
