package com.akinnova.BookReviewGrad.controller;

import com.akinnova.BookReviewGrad.dto.userdto.*;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.service.userservice.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/auth")
@AllArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/addUser")
    public ResponsePojo<UserResponseDto> addUser(@RequestBody UserCreateDto userCreateDto) {
        return userService.addUser(userCreateDto);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> allUsers(@RequestParam(defaultValue = "1") int pageNum,
                                      @RequestParam(defaultValue = "20" ) int pageSize) {
        return userService.allUsers(pageNum, pageSize);
    }

//    @GetMapping("/search")
//    public ResponseEntity<?> SearchUser(@RequestParam(required = false) String username,
//                                        @RequestParam(required = false) String phoneNumber,
//                                        @RequestParam(required = false) String email) {
//        return userService.SearchUser(username, phoneNumber, email);
//    }

    @GetMapping("/clients")
    public ResponseEntity<?> FindClients(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "20") int pageSize) {
        return userService.FindClients(pageNum, pageSize);
    }

    @GetMapping("/providers")
    public ResponseEntity<?> FindServiceProviders(@RequestParam(defaultValue = "1") int pageNum,
                                                  @RequestParam(defaultValue = "20") int pageSize) {
        return userService.FindServiceProviders(pageNum, pageSize);
    }

    @GetMapping("/admins")
    public ResponseEntity<?> FindAdmins(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "20") int pageSize) {
        return userService.FindAdmins(pageNum, pageSize);
    }

    @GetMapping("/regularUsers")
    public ResponseEntity<?> FindRegularUsers(@RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "20") int pageSize) {
        return userService.FindRegularUsers(pageNum, pageSize);
    }

    @PutMapping("/userUpdate")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userUpdateDto);
    }

    @PutMapping("/providerUpdate/{username}")
    public ResponseEntity<?> serviceProviderUpdate(@PathVariable String username, ServiceProviderUpdateDto providerUpdateDto) {
        return userService.serviceProviderUpdate(username, providerUpdateDto);
    }

    @PutMapping("/roleUpdate/{username}")
    public ResponseEntity<?> jobRoleUpdate(@PathVariable String username, AdminUpdateDto adminUpdateDto) {
        return userService.jobRoleUpdate(username, adminUpdateDto);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }
}
