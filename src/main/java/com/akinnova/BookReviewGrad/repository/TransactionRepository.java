package com.akinnova.BookReviewGrad.repository;


import com.akinnova.BookReviewGrad.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByProjectId(String serialNumber);
    Optional<List<Transaction>> findByUserId(String providerId);
    Optional<Transaction> findByInvoiceCode(String invoiceCode);
}
