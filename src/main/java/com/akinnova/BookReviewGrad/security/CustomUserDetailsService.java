package com.akinnova.BookReviewGrad.security;

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

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("user with this username not found" + username));

//        Set<GrantedAuthority> authorities = userEntity.getRoles().stream()
//                .map((role)-> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toSet());

        //I do not know if this enumSet usage will work out yet.
        Set<GrantedAuthority> authorities1 = userEntity.getEnumRoles().stream()
                .map((userRole)-> new SimpleGrantedAuthority(userRole.name())).collect(Collectors.toSet());

        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities1);
    }
}
