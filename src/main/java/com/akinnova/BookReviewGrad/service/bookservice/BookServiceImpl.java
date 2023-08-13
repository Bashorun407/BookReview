package com.akinnova.BookReviewGrad.service.bookservice;

import com.akinnova.BookReviewGrad.dto.bookdto.BookCreateDto;
import com.akinnova.BookReviewGrad.dto.bookdto.BookResponseDto;
import com.akinnova.BookReviewGrad.dto.bookdto.BookUpdateDto;
import com.akinnova.BookReviewGrad.entity.BookEntity;
import com.akinnova.BookReviewGrad.entity.enums.ReviewStatus;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.BookRepository;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import org.springframework.http.HttpStatus;
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

        //Create and save new object
      BookEntity bookEntity =  bookRepository.save(BookEntity.builder()
                        .coverImage(bookCreateDto.getCoverImage())
                        .title(bookCreateDto.getTitle())
                        .author(bookCreateDto.getAuthor())
                        .projectId(ResponseUtils.generateBookSerialNumber(10, bookCreateDto.getTitle()))
                        .reviewStatus(ReviewStatus.Pending)
                        .activeStatus(true)
                        .createdOn(LocalDateTime.now())
                .build());

      BookResponseDto bookResponseDto = BookResponseDto.builder()
              .coverImage(bookEntity.getCoverImage())
              .title(bookEntity.getTitle())
              .author(bookEntity.getAuthor())
              .projectId(bookEntity.getProjectId())
              .build();

      ResponsePojo<BookResponseDto> responsePojo = new ResponsePojo<>();
      responsePojo.setMessage("Book added successfully");
      responsePojo.setData(bookResponseDto);

        return responsePojo;
    }

    @Override
    public ResponseEntity<?> findAllBooks(int pageNum, int pageSize) {

        List<BookEntity> bookEntityList = bookRepository.findAll();
        if (bookEntityList.isEmpty())
            return new ResponseEntity<>("There are no projects currently", HttpStatus.NOT_FOUND);

        List<BookResponseDto> responseDtoList = new ArrayList<>();

        //preparing response dto
        bookEntityList.stream().filter(BookEntity::getActiveStatus).skip(pageNum).limit(pageSize).map(
                bookEntity -> BookResponseDto.builder()
                        .coverImage(bookEntity.getCoverImage())
                        .title(bookEntity.getTitle())
                        .author(bookEntity.getAuthor())
                        .projectId(bookEntity.getProjectId())
                        .build()
        ).forEach(responseDtoList::add);
        return ResponseEntity.ok()
                .header("Project page number: ", String.valueOf(pageNum))
                .header("Project page size: ", String.valueOf(pageSize))
                .header("Project total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> findBookByAuthor(String author, int pageNum, int pageSize) {

        List<BookEntity> bookEntityList = bookRepository.findByAuthor(author)
                .orElseThrow(()-> new ApiException("There are no projects by this author: " + author));

        List<BookResponseDto> responseDtoList = new ArrayList<>();

        //preparing response dto
        bookEntityList.stream().filter(BookEntity::getActiveStatus).skip(pageNum).limit(pageSize).map(
                bookEntity -> BookResponseDto.builder()
                        .coverImage(bookEntity.getCoverImage())
                        .title(bookEntity.getTitle())
                        .author(bookEntity.getAuthor())
                        .projectId(bookEntity.getProjectId())
                        .build()
        ).forEach(responseDtoList::add);
        return ResponseEntity.ok()
                .header("Project page number: ", String.valueOf(pageNum))
                .header("Project page size: ", String.valueOf(pageSize))
                .header("Project total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> findBookByTitle(String title, int pageNum, int pageSize) {

        List<BookEntity> bookEntityList = bookRepository.findByTitle(title)
                .orElseThrow(()-> new ApiException("There are no projects by this author: " + title));

        List<BookResponseDto> responseDtoList = new ArrayList<>();

        //preparing response dto
        bookEntityList.stream().filter(BookEntity::getActiveStatus).skip(pageNum).limit(pageSize).map(
                bookEntity -> BookResponseDto.builder()
                        .coverImage(bookEntity.getCoverImage())
                        .title(bookEntity.getTitle())
                        .author(bookEntity.getAuthor())
                        .projectId(bookEntity.getProjectId())
                        .build()
        ).forEach(responseDtoList::add);
        return ResponseEntity.ok()
                .header("Project page number: ", String.valueOf(pageNum))
                .header("Project page size: ", String.valueOf(pageSize))
                .header("Project total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);

    }

    @Override
    public ResponseEntity<?> findBookByProjectId(String projectId) {

        BookEntity bookEntity = bookRepository.findByProjectId(projectId)
                .orElseThrow(()-> new ApiException("There are no projects by this author: " + projectId));

        BookResponseDto responseDto = BookResponseDto.builder()
                .coverImage(bookEntity.getCoverImage())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .projectId(bookEntity.getProjectId())
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<?> updateBook(BookUpdateDto bookUpdateDto) {
        BookEntity bookEntity = bookRepository.findByProjectId(bookUpdateDto.getProjectId())
                .orElseThrow(()-> new ApiException(String.format("Book with ISBN: %s does not exist",
                        bookUpdateDto.getProjectId())));

        bookEntity.setCoverImage(bookUpdateDto.getCoverImage());
        bookEntity.setTitle(bookUpdateDto.getTitle());
        bookEntity.setAuthor(bookUpdateDto.getAuthor());
        bookEntity.setActiveStatus(true);
        bookEntity.setModifiedOn(LocalDateTime.now());

        //Save to repository
        bookRepository.save(bookEntity);

        return ResponseEntity.ok("Book updated successfully");
    }

    @Override
    public ResponseEntity<?> deleteBook(String projectId) {
        BookEntity bookEntity = bookRepository.findByProjectId(projectId)
                .filter(BookEntity::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("Book with this serialNumber: %s does not exist",
                        projectId)));

        //Change the active status of the 'deleted' book
        bookEntity.setActiveStatus(false);
        //Save to repository
        bookRepository.save(bookEntity);
        return ResponseEntity.ok("Book has been deleted");
    }

    @Override
    public ResponseEntity<?> searchBook(String author, String title, String projectId, int pageNum, int pageSize) {
        List<BookEntity> bookEntity = new ArrayList<>();
        List<BookResponseDto> responseDtoList = new ArrayList<>();

        if(StringUtils.hasText(author))
            bookEntity = bookRepository.findByAuthor(author)
                    .orElseThrow(()-> new ApiException(String.format("There are no books by this author: %s", author)));

        if(StringUtils.hasText(title))
            bookEntity = bookRepository.findByTitle(title)
                    .orElseThrow(()-> new ApiException(String.format("There are no books by this title: %s", title)));

        if(StringUtils.hasText(projectId))
            bookEntity.add(bookRepository.findByProjectId(projectId).filter(BookEntity::getActiveStatus)
                    .orElseThrow(()-> new ApiException(String.format("There are no books with this serial number: %s", projectId))));

        //Preparing response dto
        bookEntity.stream().skip(pageNum).limit(pageSize).filter(BookEntity::getActiveStatus)
                .map(
                        book -> BookResponseDto.builder()
                                .coverImage(book.getCoverImage())
                                .title(book.getTitle())
                                .author(book.getAuthor())
                                .build()
                ).forEach(responseDtoList::add);


        return ResponseEntity.ok()
                .header("Book Page Number: ", String.valueOf(pageNum))
                .header("Book Page Size: ", String.valueOf(pageSize))
                .header("Books total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }
}
