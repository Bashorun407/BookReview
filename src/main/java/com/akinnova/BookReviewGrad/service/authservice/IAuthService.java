package com.akinnova.BookReviewGrad.service.authservice;

import com.akinnova.BookReviewGrad.dto.login.LoginDto;

public interface IAuthService {
    public AuthResponse login (LoginDto loginDto);

}
