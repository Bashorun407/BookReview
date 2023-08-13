package com.akinnova.BookReviewGrad.service.transactionservice;

import com.akinnova.BookReviewGrad.dto.transactiondto.TransactionDto;
import com.akinnova.BookReviewGrad.dto.transactiondto.TransactionResponseDto;
import com.akinnova.BookReviewGrad.email.emaildto.EmailDetail;
import com.akinnova.BookReviewGrad.email.emailservice.EmailServiceImpl;
import com.akinnova.BookReviewGrad.entity.BookEntity;
import com.akinnova.BookReviewGrad.entity.ServProvider;
import com.akinnova.BookReviewGrad.entity.Transaction;
import com.akinnova.BookReviewGrad.entity.enums.ReviewStatus;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.BookRepository;
import com.akinnova.BookReviewGrad.repository.ServProviderRepository;
import com.akinnova.BookReviewGrad.repository.TransactionRepository;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements ITransactionService{
    private final UserRepository userRepository;
    private final ServProviderRepository servProviderRepository;
    private final TransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final EmailServiceImpl emailService;

    //Class Constructor
    public TransactionServiceImpl(UserRepository userRepository, ServProviderRepository servProviderRepository, TransactionRepository transactionRepository,
                                  BookRepository bookRepository, EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.servProviderRepository = servProviderRepository;
        this.transactionRepository = transactionRepository;
        this.bookRepository = bookRepository;
        this.emailService = emailService;
    }


    @Override
    public ResponseEntity<?> addTransaction(TransactionDto transactionDto) {
        //Transaction has been created and saved to repository
        Transaction transaction = transactionRepository.save(Transaction.builder()
                        .firstName(transactionDto.getFirstName())
                        .lastName(transactionDto.getLastName())
                        .otherName(transactionDto.getOtherName())
                        .projectId(transactionDto.getProjectId())
                        .providerId(transactionDto.getProviderId())
                        .amountPaid(transactionDto.getAmountPaid())
                        .invoiceCode(ResponseUtils.generateInvoiceCode(8, transactionDto.getInvoiceCode()))
                        .transactionDate(LocalDateTime.now())
                .build());

        //Once amount has been paid for a project, project is activated
        BookEntity bookEntity = bookRepository.findByProjectId(transactionDto.getProjectId())
                .orElseThrow(()-> new ApiException("There is no project by this id: " + transactionDto.getProjectId()));

        //Update and Save changes to repository
        bookEntity.setReviewStatus(ReviewStatus.Started);
        bookRepository.save(bookEntity);

        //Then notification is sent to Service provider selected by user
        ServProvider servProvider = servProviderRepository.findByProviderId(transactionDto.getProviderId())
                .orElseThrow(()-> new ApiException("There is no Service provider with id: " + transactionDto.getProviderId()));

        //Email body content
        String emailBody = "Dear " + servProvider.getLastName() + ", " + servProvider.getFirstName() + "."
                + "\n This is to notify you that you can now commence on the task assigned to you."
                + "\n Payment will be upon completion and feedback by client."
                + "\n Best regards.";

        //Email object preparation
        EmailDetail emailDetail = EmailDetail.builder()
                .subject("Project Commencement Notification")
                .body(emailBody)
                .recipient(servProvider.getEmail())
                .build();

        //Email sent here
        emailService.sendSimpleEmail(emailDetail);

        return ResponseEntity.ok("Transaction done successfully");
    }

    @Override
    public ResponseEntity<?> transactionByProjectId(String projectId) {
        Transaction transaction = transactionRepository.findByProjectId(projectId)
                .orElseThrow(()-> new ApiException("There is no transaction with project id: " + projectId));

        return ResponseEntity.ok(transaction);
    }

    @Override
    public ResponseEntity<?> transactionByProviderId(String providerId, int pageNum, int pageSize) {
        List<Transaction> transactionList = transactionRepository.findByProviderId(providerId)
                .orElseThrow(()-> new ApiException("There are no transaction with provider id: " + providerId));

        List<TransactionResponseDto> responseDtoList = new ArrayList<>();

        //preparing response dto
        transactionList.stream().skip(pageNum).limit(pageSize).map(
                transaction -> TransactionResponseDto.builder()
                        .firstName(transaction.getFirstName())
                        .lastName(transaction.getLastName())
                        .otherName(transaction.getOtherName())
                        .amountPaid(transaction.getAmountPaid())
                        .invoiceCode(transaction.getInvoiceCode())
                        .transactionDate(transaction.getTransactionDate())
                        .build()
        ).forEach(responseDtoList::add);


        return ResponseEntity.ok()
                .header("Transaction Page Number: ", String.valueOf(pageNum))
                .header("Transaction page size: ", String.valueOf(pageSize))
                .header("Transaction Total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> transactionByInvoiceCode(String transactionCode) {

        Transaction transaction = transactionRepository.findByInvoiceCode(transactionCode)
                .orElseThrow(()-> new ApiException("There is no transaction with transaction code: " + transactionCode));

        return ResponseEntity.ok(transaction);
    }
}
