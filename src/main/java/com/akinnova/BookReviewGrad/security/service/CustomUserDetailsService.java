package com.akinnova.BookReviewGrad.security.service;



import com.akinnova.BookReviewGrad.entity.UserEntity;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public CustomUserDetailsService(UserRepository attendeesRepository){
        this.userRepository = attendeesRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userRepository.existsByEmail(email)) {
            UserEntity userCredential = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

            Set<GrantedAuthority> authorities = userCredential.getRoleName().stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toSet());

            return new User(
                    userCredential.getEmail(),
                    userCredential.getPassword(),
                    authorities
            );
        } else {

            throw new UsernameNotFoundException("User not found with username: " + email);
        }
    }

}

