package com.akinnova.BookReviewGrad.response;

import lombok.Data;

@Data
public class ResponsePojo <T>{
    String statusCode;
    boolean success = true;
    String message;
    T data;
    String pageNumber;
    String pageSize;
    String dataCount;
    boolean previous;
    boolean next;

    public ResponsePojo(String statusCode, boolean success, String message, T data){
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ResponsePojo(String statusCode, boolean success, String message,
                        T data, int pageNumber, int pageSize, int dataCount){
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
        this.data = data;
        this.pageNumber =  "X- Page Number: " + pageNumber;
        this.pageSize = "X- Page Size: " + pageSize;
        this.dataCount = "X-Number of Items: " + dataCount;
        this.previous = true;
        this.next = true;
    }
}
