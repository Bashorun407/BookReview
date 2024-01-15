package com.akinnova.BookReviewGrad.repository;

import com.akinnova.BookReviewGrad.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findByUsername(String username);
}
