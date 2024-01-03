//package com.akinnova.BookReviewGrad.service.userservice;
//
//import com.akinnova.BookReviewGrad.dto.userdto.*;
//import com.akinnova.BookReviewGrad.response.ResponsePojo;
//import org.springframework.http.ResponseEntity;
//
//public interface IUserService {
//
//    ResponsePojo<UserResponseDto> addUser(UserCreateDto userCreateDto);
//    ResponsePojo<UserResponseDto> addAdmin(UserCreateDto userCreateDto);
//    ResponseEntity<?> allUsers(int pageNum, int pageSize);
//    //ResponseEntity<?> SearchUser(String username, String phoneNumber, String email);
//    ResponseEntity<?> FindClients(int pageNum, int pageSize);
//    ResponseEntity<?> FindServiceProviders(int pageNum, int pageSize);
//    ResponseEntity<?> FindRegularUsers(int pageNum, int pageSize);
//    ResponseEntity<?> FindAdmins(int pageNum, int pageSize);
//    ResponseEntity<?> updateUser(UserUpdateDto userUpdateDto);
//    ResponseEntity<?> serviceProviderUpdate(String username, ServiceProviderUpdateDto providerUpdateDto);
//    ResponseEntity<?> jobRoleUpdate(String username, AdminUpdateDto adminUpdateDto);
//    ResponseEntity<?> deleteUser(String username);
//
//}
