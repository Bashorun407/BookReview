package com.akinnova.BookReviewGrad.response;

import lombok.Data;

import java.util.List;

@Data
public class MetaData <T>{
    boolean previous;
    boolean next;
    String details;

    public MetaData(List<T> objectList, int pageNum, int pageSize) {
        this.previous = pageNum > 1;
        this.next = pageNum < objectList.size();
        details = String.format("Current page: %d, items on page: %d, total items: %d", pageNum, pageSize, objectList.size());
    }
}
