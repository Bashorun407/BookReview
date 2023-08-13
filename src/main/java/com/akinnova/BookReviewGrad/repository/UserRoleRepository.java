package com.akinnova.BookReviewGrad.repository;

import com.akinnova.BookReviewGrad.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Boolean existsByRoleName(String roleName);
}
