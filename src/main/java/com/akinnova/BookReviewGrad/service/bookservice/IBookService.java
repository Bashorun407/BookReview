package com.akinnova.BookReviewGrad.service.bookservice;

import com.akinnova.BookReviewGrad.dto.bookdto.BookCreateDto;
import com.akinnova.BookReviewGrad.dto.bookdto.BookResponseDto;
import com.akinnova.BookReviewGrad.dto.bookdto.BookUpdateDto;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import org.springframework.http.ResponseEntity;

public interface IBookService {
    ResponsePojo<BookResponseDto> addBook(BookCreateDto bookCreateDto);
    ResponseEntity<?> findAllBooks(int pageNum, int pageSize);
    ResponseEntity<?> findBookByAuthor(String author, int pageNum, int pageSize);
    ResponseEntity<?> findBookByTitle(String title, int pageNum, int pageSize);
    ResponseEntity<?> findBookByProjectId(String projectId);
    ResponseEntity<?> updateBook(BookUpdateDto bookUpdateDto);
    ResponseEntity<?> deleteBook(String projectId);
    ResponseEntity<?> searchBook(String author, String title, String projectId, int pageNum, int pageSize);

}
