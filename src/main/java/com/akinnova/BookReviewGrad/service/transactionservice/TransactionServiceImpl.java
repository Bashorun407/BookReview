package com.akinnova.BookReviewGrad.service.transactionservice;

import com.akinnova.BookReviewGrad.dto.transactiondto.CartItemPurchaseDto;
import com.akinnova.BookReviewGrad.email.emaildto.EmailDetail;
import com.akinnova.BookReviewGrad.email.emailservice.EmailServiceImpl;
import com.akinnova.BookReviewGrad.entity.Transaction;
import com.akinnova.BookReviewGrad.entity.UserEntity;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.TransactionRepository;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import com.akinnova.BookReviewGrad.response.ResponsePojo;

import com.akinnova.BookReviewGrad.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public ResponsePojo<Transaction> cashPayment(CartItemPurchaseDto cartItemPurchaseDto) {

        // TODO: 7/2/2023 (Retrieve cart items, change checkout status to true and make payment via customer card
        //To retrieve customer details from customer repository
        UserEntity userEntity = userRepository.findByUsername(cartItemPurchaseDto.getUsername())
                .orElseThrow(()-> new ApiException("User does not exist by this username"));


        //Send email to notify payment to customer
        EmailDetail emailDetail = EmailDetail.builder()
                .subject("Successful Purchase From Akinova BookStores")
                .body("Dear " + userEntity.getLastName() + ", " + userEntity.getFirstName() +
                        "\n You received this notification from a purchase you made on our online bookstore.\n"
                        + "Details of purchase are :"
                        + "\n Cost of purchase: " + cartItemPurchaseDto.getBalance()
                        + "\n Balance: " + cartItemPurchaseDto.getBalance().toString()
                        + "\n Thank you for patronizing us."
                        + "\n\n Akinnova BookStore."  )
                .recipient(userEntity.getEmail())
                .build();
        emailService.sendSimpleEmail(emailDetail);

        //Transaction details
        Transaction transaction = Transaction.builder()
                .username(cartItemPurchaseDto.getUsername())
                .amountPaid(cartItemPurchaseDto.getBalance())
                .invoiceCode(ResponseUtils.generateInvoiceCode(6, cartItemPurchaseDto.getUsername()))
                .transactionDate(LocalDateTime.now())
                .build();

        //Save transaction in the Transaction repository
        transactionRepository.save(transaction);

        ResponsePojo<Transaction> responsePojo = new ResponsePojo<>();
        responsePojo.setStatusCode(ResponseUtils.OK);
        responsePojo.setMessage("Transaction is successful.");
        responsePojo.setData(transaction);
        return responsePojo;
    }

    @Override
    public ResponseEntity<?> transactionByUsername(String username) {
        List<Transaction> transactionList = transactionRepository.findByUsername(username)
                .orElseThrow(()-> new ApiException("There are no transactions yet"));

        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> transactionByInvoiceCode(String transactionCode) {
        Transaction transaction = transactionRepository.findByInvoiceCode(transactionCode)
                .orElseThrow(()-> new ApiException(String.format("There is no transaction for this invoice: %s", transactionCode)));

        return new ResponseEntity<>(transaction, HttpStatus.NOT_FOUND);

    }

}
