package com.akinnova.BookReviewGrad.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    //1) Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private final String[] WHITE_LIST_URL = {"/api/v1/book/auth/(.*)", "/api/v1/book/auth/(.*)"};

    //2) Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize->
                        authorize.requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/book/auth/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/book/auth/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/comment/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/comment/auth/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/comment/auth/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/comment/auth/(.*)").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/rates/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/rates/auth/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/rates/auth/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/rates/auth/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/user/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/user/auth/**)").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/user/auth/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/user/auth/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/provider/auth/(.*)").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/provider/auth/(.*)").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/provider/auth/(.*)").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/provider/auth/(.*)").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/transaction/auth/(.*)").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/transaction/auth/(.*)").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/email/auth/(.*)").permitAll()
                                .requestMatchers("**").permitAll()
                                .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    //3)Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
