package com.akinnova.BookReviewGrad.dto.transactiondto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private String firstName;
    private String lastName;
    private String otherName;
    private String projectId;
    private String providerId;
    private Double amountPaid;
    private String invoiceCode;
    private LocalDateTime transactionDate;
}
