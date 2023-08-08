package com.akinnova.BookReviewGrad.response;

import lombok.Data;

@Data
public class ResponsePojo <T>{
    String statusCode = "200";
    boolean success = true;
    String message;
    T data;
}
