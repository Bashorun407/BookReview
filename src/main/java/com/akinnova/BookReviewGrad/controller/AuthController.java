package com.akinnova.BookReviewGrad.controller;

import com.akinnova.BookReviewGrad.dto.login.LoginDto;
import com.akinnova.BookReviewGrad.dto.userdto.UserCreateDto;
import com.akinnova.BookReviewGrad.dto.userdto.UserResponseDto;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.service.authservice.AuthResponse;
import com.akinnova.BookReviewGrad.service.authservice.AuthServiceImpl;
import com.akinnova.BookReviewGrad.service.authservice.IAuthService;
import com.akinnova.BookReviewGrad.service.userservice.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;
    private final IUserService userService;

    @PostMapping("/addUser")
    public ResponsePojo<UserResponseDto> addUser(@RequestBody UserCreateDto userCreateDto) {
        return userService.addUser(userCreateDto);
    }
    @PostMapping("/addAdmin")
    public ResponsePojo<UserResponseDto> addAdmin(@RequestBody UserCreateDto userCreateDto) {
        return userService.addAdmin(userCreateDto);
    }


    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @GetMapping("/providers")
    public ResponseEntity<?> FindAllServiceProviders(@RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "20") int pageSize) {
        return userService.FindServiceProviders(pageNum, pageSize);
    }

}
