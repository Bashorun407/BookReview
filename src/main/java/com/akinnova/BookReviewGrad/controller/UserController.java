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




//    @GetMapping("/search")
//    public ResponseEntity<?> SearchUser(@RequestParam(required = false) String username,
//                                        @RequestParam(required = false) String phoneNumber,
//                                        @RequestParam(required = false) String email) {
//        return userService.SearchUser(username, phoneNumber, email);
//    }



    @PutMapping("/userUpdate")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userUpdateDto);
    }

    @PutMapping("/providerUpdate/{username}")
    public ResponseEntity<?> serviceProviderUpdate(@PathVariable String username, @RequestBody ServiceProviderUpdateDto providerUpdateDto) {
        return userService.serviceProviderUpdate(username, providerUpdateDto);
    }

    // TODO: 18/09/2023 There should be user update to upload image
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }
}
