package com.akinnova.BookReviewGrad.controller;

import com.akinnova.BookReviewGrad.dto.transactiondto.TransactionDto;
import com.akinnova.BookReviewGrad.service.transactionservice.TransactionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction/auth")
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    // TODO: 18/09/2023 Transaction should be able to consume api from payment platforms 

    @PostMapping("/addTransaction")
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.addTransaction(transactionDto);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> transactionByProjectId(@PathVariable String projectId) {
        return transactionService.transactionByProjectId(projectId);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<?> transactionByProviderId(@PathVariable String providerId,
                                                     @RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "20") int pageSize) {
        return transactionService.transactionByProviderId(providerId, pageNum, pageSize);
    }

    @GetMapping("/invoice/{transactionCode}")
    public ResponseEntity<?> transactionByInvoiceCode(@PathVariable String transactionCode) {
        return transactionService.transactionByInvoiceCode(transactionCode);
    }
}
