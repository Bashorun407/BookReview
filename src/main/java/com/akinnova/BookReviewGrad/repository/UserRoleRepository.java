package com.akinnova.BookReviewGrad.repository;

import com.akinnova.BookReviewGrad.entity.UserRoles;
import com.akinnova.BookReviewGrad.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {
    Boolean existsByRoleName(UserRole roleName);
}
