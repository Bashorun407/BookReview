package com.akinnova.BookReviewGrad.controller;

import com.akinnova.BookReviewGrad.dto.login.LoginDto;
import com.akinnova.BookReviewGrad.service.authservice.AuthResponse;
import com.akinnova.BookReviewGrad.service.authservice.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public AuthResponse login(LoginDto loginDto) {
        return authService.login(loginDto);
    }
}
