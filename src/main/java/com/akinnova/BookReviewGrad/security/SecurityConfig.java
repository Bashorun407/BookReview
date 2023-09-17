package com.akinnova.BookReviewGrad.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

//    private final UserDetails userDetails;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    //1) Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    private final String[] WHITE_LIST_URL = {"/api/v1/comment/auth/**", "/api/v1/rates/auth/**",
             "/api/v1/provider/auth/(.*)", "/api/v1/transaction/auth/(.*)", "/api/v1/email/auth/(.*)"};

    //2) Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement((sessionManagement)-> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize->
                        authorize.requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/login/auth").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/user/auth/addUser").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/user/auth/allUsers").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/user/auth/clients").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/user/auth/providers").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/user/auth/admins").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/user/auth/regularUsers").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/v1/user/auth/userUpdate").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/user/auth/providerUpdate").hasAuthority("REGULAR_USER")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/user/auth/roleUpdate").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/user/auth/delete").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/project/auth/addProject").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/project/auth/allProjects").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/project/auth/username/**").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/project/auth/title/**").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/project/auth/projectId/**").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/project/auth/pending").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/project/auth/started").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/project/auth/completed").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/project/auth/updated").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/project/auth/serviceProviderUpdate").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/project/auth/adminUpdate").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/project/auth/delete/**").hasAnyAuthority("ADMIN", "REGULAR_USER")
                                .requestMatchers("/**","/swagger-ui/index.html").permitAll()

                                //.requestMatchers("**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    //3)Authentication Manager


}
