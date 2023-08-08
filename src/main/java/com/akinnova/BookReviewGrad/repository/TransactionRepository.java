package com.akinnova.BookReviewGrad.repository;

import com.akinnova.BookReviewGrad.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<List<Transaction>> findByUsername(String username);
    Optional<Transaction> findByInvoiceCode(String invoiceCode);
}
