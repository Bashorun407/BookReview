package com.akinnova.BookReviewGrad.service.transactionservice;

import com.akinnova.BookReviewGrad.dto.transactiondto.TransactionDto;
import com.akinnova.BookReviewGrad.dto.transactiondto.TransactionResponseDto;
import com.akinnova.BookReviewGrad.email.emaildto.EmailDetail;
import com.akinnova.BookReviewGrad.email.emailservice.EmailServiceImpl;
import com.akinnova.BookReviewGrad.entity.Project;
import com.akinnova.BookReviewGrad.entity.Transaction;
import com.akinnova.BookReviewGrad.entity.UserEntity;
import com.akinnova.BookReviewGrad.enums.ProjectStartApproval;
import com.akinnova.BookReviewGrad.response.ResponseType;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.ProjectRepository;
import com.akinnova.BookReviewGrad.repository.TransactionRepository;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import com.akinnova.BookReviewGrad.response.EmailResponse;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import com.akinnova.BookReviewGrad.utilities.Utility;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements ITransactionService{
    private EmailServiceImpl emailService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ProjectRepository bookRepository;

    @Override
    public ResponseEntity<?> addTransaction(TransactionDto transactionDto) {
        //Transaction has been created and saved to repository
        transactionRepository.save(Transaction.builder()
                .firstName(transactionDto.getFirstName())
                .lastName(transactionDto.getLastName())
                .otherName(transactionDto.getOtherName())
                .projectId(transactionDto.getProjectId())
                .userId(transactionDto.getUserId())
                .amountPaid(transactionDto.getAmountPaid())
                .invoiceCode(Utility.generateInvoiceCode(8, transactionDto.getProjectId()))
                .transactionDate(LocalDateTime.now())
                .build());

        //Once amount has been paid for a project, project is activated
        Project project = bookRepository.findByProjectId(transactionDto.getProjectId())
                .orElseThrow(()-> new ApiException(ResponseUtils.NO_PROJECT_BY_ID + transactionDto.getProjectId()));

        //Update and Save changes to repository
        project.setProjectStartApproval(ProjectStartApproval.APPROVED);
        bookRepository.save(project);

        //Then notification is sent to Service provider selected by user
        UserEntity userEntity = userRepository.findByUserId(transactionDto.getUserId())
                .orElseThrow(()-> new ApiException(ResponseUtils.NO_SERVICE_PROVIDER_BY_ID + transactionDto.getUserId()));

        //Email object preparation
        EmailDetail emailDetail = EmailDetail.builder()
                .subject(EmailResponse.PROJECT_COMMENCEMENT_SUBJECT)
                .body(String.format(EmailResponse.PROJECT_COMMENCEMENT_MAIL, userEntity.getLastName(), userEntity.getFirstName()))
                .recipient(userEntity.getEmail())
                .build();

        //Email sent here
        emailService.sendSimpleEmail(emailDetail);

        return ResponseEntity.ok(ResponseUtils.SUCCESSFUL_TRANSACTION);
    }

    @Override
    public ResponseEntity<?> transactionByProjectId(String projectId) {
        Transaction transaction = transactionRepository.findByProjectId(projectId)
                .orElseThrow(()-> new ApiException(ResponseUtils.NO_TRANSACTION_WITH_ID + projectId));

        return ResponseEntity.ok(transaction);
    }

    @Override
    public ResponseEntity<?> transactionByProviderId(String providerId, int pageNum, int pageSize) {

        List<Transaction> transactionList = transactionRepository.findByUserId(providerId)
                .orElseThrow(()-> new ApiException(ResponseUtils.NO_TRANSACTION_WITH_ID + providerId));

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, String.format("Transaction by id: %s", providerId),
                transactionList.stream().skip(pageNum - 1).limit(pageSize).map(TransactionResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> transactionByInvoiceCode(String transactionCode) {

        Transaction transaction = transactionRepository.findByInvoiceCode(transactionCode)
                .orElseThrow(()-> new ApiException(ResponseUtils.NO_TRANSACTION_WITH_ID + transactionCode));

        return ResponseEntity.ok(transaction);
    }
}
