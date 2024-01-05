package com.akinnova.BookReviewGrad.repository;

import com.akinnova.BookReviewGrad.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    //Optional<Rating> findByUsername(String username);
}
