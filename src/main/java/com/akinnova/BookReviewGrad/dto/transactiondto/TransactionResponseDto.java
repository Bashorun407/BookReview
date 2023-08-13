package com.akinnova.BookReviewGrad.dto.transactiondto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponseDto {
    private String firstName;
    private String lastName;
    private String otherName;
    private Double amountPaid;
    private String invoiceCode;
    private LocalDateTime transactionDate;

}
