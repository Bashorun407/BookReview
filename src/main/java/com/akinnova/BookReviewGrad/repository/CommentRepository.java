package com.akinnova.BookReviewGrad.repository;

import com.akinnova.BookReviewGrad.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<List<CommentEntity>> findByUsername(String username);
    Optional<List<CommentEntity>> findByTitle(String title);
}
