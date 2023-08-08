package com.akinnova.BookReviewGrad.repository;

import com.akinnova.BookReviewGrad.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    Optional<RatingEntity> findByTitle(String title);
}
