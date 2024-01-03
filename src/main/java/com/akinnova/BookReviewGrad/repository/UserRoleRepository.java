package com.akinnova.BookReviewGrad.repository;

import com.akinnova.BookReviewGrad.entity.UserRole;
import com.akinnova.BookReviewGrad.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Boolean existsByRoleName(RoleName roleName);
    Optional<UserRole> findByRoleName(String roleName);
}
