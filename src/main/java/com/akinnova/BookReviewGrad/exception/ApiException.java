package com.akinnova.BookReviewGrad.exception;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
