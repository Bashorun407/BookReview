//package com.akinnova.BookReviewGrad.security;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//import javax.naming.AuthenticationException;
//import java.io.IOException;
//
//@Component
//public class JwtAuthenticationEntryPoint {
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException, IOException {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
//    }
//}
