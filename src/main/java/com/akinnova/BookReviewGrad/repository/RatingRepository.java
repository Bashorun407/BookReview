package com.akinnova.BookReviewGrad.repository;

import com.akinnova.BookReviewGrad.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface RatingRepository extends JpaRepository<Rating, Long> {
    //Optional<Rating> findByUsername(String username);
}
