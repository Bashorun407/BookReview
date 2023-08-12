package com.akinnova.BookReviewGrad.service.transactionservice;

import com.akinnova.BookReviewGrad.email.emailservice.EmailServiceImpl;
import com.akinnova.BookReviewGrad.entity.Transaction;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.TransactionRepository;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements ITransactionService{
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    private final EmailServiceImpl emailService;

    //Class Constructor
    public TransactionServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository,
                                   EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.emailService = emailService;
    }


    @Override
    public ResponseEntity<?> transactionByUsername(String username) {
        return null;
    }

    @Override
    public ResponseEntity<?> transactionByInvoiceCode(String transactionCode) {
        return null;
    }
}
