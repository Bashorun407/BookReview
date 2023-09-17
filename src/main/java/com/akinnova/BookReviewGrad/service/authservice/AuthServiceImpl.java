package com.akinnova.BookReviewGrad.service.authservice;

import com.akinnova.BookReviewGrad.dto.login.LoginDto;
import com.akinnova.BookReviewGrad.entity.UserEntity;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import com.akinnova.BookReviewGrad.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements IAuthService{
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public AuthResponse login(LoginDto loginDto) {

        boolean isUserExist = userRepository.existsByUsername(loginDto.getUsername());

        Authentication authentication;
//            authResponse = ;
//        AuthResponse authResponse = new AuthResponse();

        if(isUserExist){
            UserEntity user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(()-> new ApiException("Username not found"));

            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return AuthResponse.builder()
                    .token(jwtTokenProvider.generateToken(authentication))
                    .build();
        }else {
            return AuthResponse.builder()
                    .token("User doe not exist")
                    .build();
        }

    }
}
