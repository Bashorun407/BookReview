package com.akinnova.BookReviewGrad.email.emaildto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDetail {
    private String recipient;
    private String subject;
    private String body;
    private String filePath;
}
