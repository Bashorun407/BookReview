package com.akinnova.BookReviewGrad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "transaction_table")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String firstName;
    private String lastName;
    private String otherName;
    private String serialNumber;
    private Double amountPaid;
    private String invoiceCode;
    private LocalDateTime transactionDate;
}
