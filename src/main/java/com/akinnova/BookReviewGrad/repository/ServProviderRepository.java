package com.akinnova.BookReviewGrad.repository;

import com.akinnova.BookReviewGrad.entity.ServProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServProviderRepository extends JpaRepository<ServProvider, Long> {

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<ServProvider> findByUsername(String username);
    Optional<ServProvider> findByEmail(String email);
    Optional<ServProvider> findByProviderId(String providerId);
}
