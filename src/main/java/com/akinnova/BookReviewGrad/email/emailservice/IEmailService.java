package com.akinnova.BookReviewGrad.email.emailservice;

import com.akinnova.BookReviewGrad.email.emaildto.EmailDetail;
import org.springframework.http.ResponseEntity;

public interface IEmailService {
    ResponseEntity<?> sendSimpleEmail(EmailDetail emailDetail);
    ResponseEntity<?> sendEmailWithAttachment(EmailDetail emailDetail);
}
