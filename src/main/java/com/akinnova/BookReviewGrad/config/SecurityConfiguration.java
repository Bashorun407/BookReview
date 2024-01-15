//package com.akinnova.BookReviewGrad.config;
//
//import com.akinnova.BookReviewGrad.config.security.filters.CustomAuthenticationFilter;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@AllArgsConstructor
//public class SecurityConfiguration {
//
//    private final CustomAuthenticationFilter customAuthenticationFilter;
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//        return httpSecurity
//                .addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests().anyRequest().authenticated()
//                .and().build();
//    }
//}
