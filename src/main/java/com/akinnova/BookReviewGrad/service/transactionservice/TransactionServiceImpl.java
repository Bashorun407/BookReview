package com.akinnova.BookReviewGrad.service.transactionservice;

import com.akinnova.BookReviewGrad.dto.serviceproviderdto.ServProviderResponseDto;
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
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements ITransactionService{


    private EmailServiceImpl emailService;
    private final ServProviderRepository servProviderRepository;
    private final TransactionRepository transactionRepository;
    private final BookRepository bookRepository;

    @Override
    public ResponseEntity<?> addTransaction(TransactionDto transactionDto) {
        //Transaction has been created and saved to repository
        transactionRepository.save(Transaction.builder()
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

        //Once the book review project's money has been paid, update and save changes to repository
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
                TransactionResponseDto::new
        ).forEach(responseDtoList::add);


        ResponsePojo<List<TransactionResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "Successful applications list", responseDtoList, pageNum, pageSize, responseDtoList.size());

        return ResponseEntity.ok(responsePojo);
    }

    @Override
    public ResponseEntity<?> transactionByInvoiceCode(String transactionCode) {

        Transaction transaction = transactionRepository.findByInvoiceCode(transactionCode)
                .orElseThrow(()-> new ApiException("There is no transaction with transaction code: " + transactionCode));

        return ResponseEntity.ok(transaction);
    }
}
