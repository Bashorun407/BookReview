package com.akinnova.BookReviewGrad.service.transactionservice;

import com.akinnova.BookReviewGrad.dto.transactiondto.CartItemPurchaseDto;
import com.akinnova.BookReviewGrad.entity.Transaction;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ITransactionService {
    ResponsePojo<Transaction> cashPayment(CartItemPurchaseDto cartItemPurchaseDto);
    ResponseEntity<?> transactionByUsername(String username);
    ResponseEntity<?> transactionByInvoiceCode(String transactionCode);
}
