package com.akinnova.BookReviewGrad.service.bookservice;

import com.akinnova.BookReviewGrad.dto.bookdto.BookCreateDto;
import com.akinnova.BookReviewGrad.dto.bookdto.BookResponseDto;
import com.akinnova.BookReviewGrad.dto.bookdto.BookUpdateDto;
import com.akinnova.BookReviewGrad.entity.BookEntity;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.BookRepository;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public ResponsePojo<BookResponseDto> addBook(BookCreateDto bookCreateDto) {
        boolean check = bookRepository.existsByISBN(bookCreateDto.getISBN());
        if(check)
            throw new ApiException(String.format("Book with ISBN: %s already exists", bookCreateDto.getISBN()));

        //Create and save new object
      BookEntity bookEntity =  bookRepository.save(BookEntity.builder()
                        .coverImage(bookCreateDto.getCoverImage())
                        .title(bookCreateDto.getTitle())
                        .author(bookCreateDto.getAuthor())
                        .genre(bookCreateDto.getGenre())
                        .publicationYear(bookCreateDto.getPublicationYear())
                        .ISBN(bookCreateDto.getISBN())
                        .serialNumber(ResponseUtils.generateBookSerialNumber(10, bookCreateDto.getTitle()))
                        .summary(bookCreateDto.getSummary())
                        .activeStatus(true)
                        .createdOn(LocalDateTime.now())
                .build());

      BookResponseDto bookResponseDto = BookResponseDto.builder()
              .coverImage(bookEntity.getCoverImage())
              .title(bookEntity.getTitle())
              .author(bookEntity.getAuthor())
              .summary(bookEntity.getSummary())
              .build();

      ResponsePojo<BookResponseDto> responsePojo = new ResponsePojo<>();
      responsePojo.setMessage("Book added successfully");
      responsePojo.setData(bookResponseDto);

        return responsePojo;
    }

    @Override
    public ResponseEntity<?> findAllBooks(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ResponseEntity<?> findBookByAuthor(String author, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ResponseEntity<?> findBookByTitle(String title, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ResponseEntity<?> findBookByGenre(String genre, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ResponseEntity<?> findBookByISBN(String ISBN) {
        return null;
    }

    @Override
    public ResponseEntity<?> findBookBySerialNumber(String serialNumber) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateBook(BookUpdateDto bookUpdateDto) {
        BookEntity bookEntity = bookRepository.findByISBN(bookUpdateDto.getISBN())
                .orElseThrow(()-> new ApiException(String.format("Book with ISBN: %s does not exist",
                        bookUpdateDto.getISBN())));
        bookEntity.setCoverImage(bookUpdateDto.getCoverImage());
        bookEntity.setTitle(bookUpdateDto.getTitle());
        bookEntity.setAuthor(bookUpdateDto.getAuthor());
        bookEntity.setGenre(bookUpdateDto.getGenre());
        bookEntity.setSummary(bookUpdateDto.getSummary());
        bookEntity.setActiveStatus(true);

        //Save to repository
        bookRepository.save(bookEntity);

        return ResponseEntity.ok("Book updated successfully");
    }

    @Override
    public ResponseEntity<?> deleteBook(String serialNumber) {
        BookEntity bookEntity = bookRepository.findBySerialNumber(serialNumber)
                .filter(BookEntity::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("Book with this serialNumber: %s does not exist",
                        serialNumber)));

        //Change the active status of the 'deleted' book
        bookEntity.setActiveStatus(false);
        //Save to repository
        bookRepository.save(bookEntity);
        return ResponseEntity.ok("Book has been deleted");
    }

    @Override
    public ResponseEntity<?> searchBook(String author, String title, String genre, String ISBN,
                                        String serialNumber, int pageNum, int pageSize) {
        List<BookEntity> bookEntity = new ArrayList<>();
        List<BookResponseDto> responseDtoList = new ArrayList<>();

        if(StringUtils.hasText(author))
            bookEntity = bookRepository.findByAuthor(author)
                    .orElseThrow(()-> new ApiException(String.format("There are no books by this author: %s", author)));

        if(StringUtils.hasText(title))
            bookEntity = bookRepository.findByTitle(title)
                    .orElseThrow(()-> new ApiException(String.format("There are no books by this title: %s", title)));

        if(StringUtils.hasText(genre))
            bookEntity = bookRepository.findByGenre(genre)
                    .orElseThrow(()-> new ApiException(String.format("There are no books by this genre: %s", genre)));

        if(StringUtils.hasText(ISBN))
            bookEntity.add(bookRepository.findByISBN(ISBN).filter(BookEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("There are no books by this ISBN: %s", ISBN))));

        if(StringUtils.hasText(serialNumber))
            bookEntity.add(bookRepository.findBySerialNumber(serialNumber).filter(BookEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("There are no books with this serial number: %s", serialNumber))));

        //Preparing response dto
        bookEntity.stream().skip(pageNum).limit(pageSize).filter(BookEntity::getActiveStatus)
                .map(
                        book -> BookResponseDto.builder()
                                .coverImage(book.getCoverImage())
                                .title(book.getTitle())
                                .author(book.getAuthor())
                                .summary(book.getSummary())
                                .build()
                ).forEach(responseDtoList::add);


        return ResponseEntity.ok()
                .header("Book Page Number: ", String.valueOf(pageNum))
                .header("Book Page Size: ", String.valueOf(pageSize))
                .header("Books total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }
}
