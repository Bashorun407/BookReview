package com.akinnova.BookReviewGrad.service.userservice;

import com.akinnova.BookReviewGrad.dto.userdto.UserCreateDto;
import com.akinnova.BookReviewGrad.dto.userdto.UserResponseDto;
import com.akinnova.BookReviewGrad.dto.userdto.UserUpdateDto;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    ResponsePojo<UserResponseDto> addUser(UserCreateDto clientCreateDto);
    ResponseEntity<?> allUsers(int pageNum, int pageSize);
    ResponseEntity<?> SearchUser(String username, String phoneNumber, String email);
    ResponseEntity<?> updateUser(UserUpdateDto clientUpdateDto);
    ResponseEntity<?> deleteUser(String username);

}
