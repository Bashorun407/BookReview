//package com.akinnova.BookReviewGrad.service.authservice;
//
//import com.akinnova.BookReviewGrad.dto.login.LoginDto;
//import com.akinnova.BookReviewGrad.repository.UserRepository;
//
//import com.akinnova.BookReviewGrad.security.config.JwtTokenProvider;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//
//@Service
//@Slf4j
//public class AuthServiceImpl implements IAuthService{
//    private final UserRepository userRepository;
//    private final AuthenticationManager authenticationManager;
//    private PasswordEncoder passwordEncoder;
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
//                           PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
//        this.userRepository = userRepository;
//        this.authenticationManager = authenticationManager;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//
//    @Override
//    public AuthResponse login(LoginDto loginDto) {
//
//        boolean isUserExist = userRepository.existsByEmail(loginDto.getEmail());
//        log.info("UserEntity Status: "+ isUserExist);
//
////            authResponse = ;
////        AuthResponse authResponse = new AuthResponse();
//
//        if(isUserExist){
////            Authentication authentication;
////
////            UserEntity userEntity = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(()-> new ApiException("Username not found"));
////
////            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
////
////            SecurityContextHolder.getContext().setAuthentication(authentication);
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            authentication.getName();
//            authentication.getCredentials();
//            return AuthResponse.builder()
//                    .token(jwtTokenProvider.generateToken(authentication))
//                    .build();
//        }else {
//            return AuthResponse.builder()
//                    .token("UserEntity does not exist")
//                    .build();
//        }
//
//    }
//
//}
