package com.akinnova.BookReviewGrad.service.userservice;

import com.akinnova.BookReviewGrad.entity.UserEntity;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class PredicateUser {

    private final UserRepository userRepository;

    public PredicateUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    ResponseEntity<?> findUserByParameters(String firstName, String lastName, String username, String email){
        //I don't know what to write yet.
        return ResponseEntity.ok(null);
    }
}
