package com.akinnova.BookReviewGrad.service.userservice;

import com.akinnova.BookReviewGrad.dto.userdto.UserCreateDto;
import com.akinnova.BookReviewGrad.dto.userdto.UserResponseDto;
import com.akinnova.BookReviewGrad.dto.userdto.UserUpdateDto;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    ResponsePojo<UserResponseDto> addUser(UserCreateDto userCreateDto);
    ResponseEntity<?> allUsers(int pageNum, int pageSize);
    ResponseEntity<?> SearchUser(String username, String phoneNumber, String email);
    ResponseEntity<?> FindClients(int pageNum, int pageSize);
    ResponseEntity<?> FindServiceProviders(int pageNum, int pageSize);
    ResponseEntity<?> FindAdmins(int pageNum, int pageSize);
    ResponseEntity<?> updateUser(UserUpdateDto userUpdateDto);
    ResponseEntity<?> deleteUser(String username);

}
