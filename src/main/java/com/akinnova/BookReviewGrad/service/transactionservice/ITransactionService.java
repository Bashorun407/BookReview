package com.akinnova.BookReviewGrad.service.transactionservice;
import org.springframework.http.ResponseEntity;

public interface ITransactionService {

    ResponseEntity<?> transactionByUsername(String username);
    ResponseEntity<?> transactionByInvoiceCode(String transactionCode);
}
