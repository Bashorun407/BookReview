package com.akinnova.BookReviewGrad.repository;

import com.akinnova.BookReviewGrad.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<List<Project>> findByTitle(String title);


}
