package com.akinnova.BookReviewGrad.dto.transactiondto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private String firstName;
    private String lastName;
    private String otherName;
    private String projectId;
    private String userId;
    private Double amountPaid;
    //private String invoiceCode;
    private LocalDateTime transactionDate;

    public TransactionDto(String firstName, String lastName, String otherName, String projectId, String userId, Double amountPaid, LocalDateTime transactionDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.projectId = projectId;
        this.userId = userId;
        this.amountPaid = amountPaid;
        //this.invoiceCode = invoiceCode;
        this.transactionDate = transactionDate;
    }

    public TransactionDto(){}
}
