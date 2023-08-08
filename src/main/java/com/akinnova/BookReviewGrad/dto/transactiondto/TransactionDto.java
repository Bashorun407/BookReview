package com.akinnova.BookReviewGrad.dto.transactiondto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private String firstName;
    private String lastName;
    private String otherName;
    private String bankName;
    private String accountNumber;
    private Double amountPaid;
    private String invoiceCode;
    private LocalDateTime transactionDate;
}
