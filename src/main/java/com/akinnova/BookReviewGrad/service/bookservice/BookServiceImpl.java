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
                        .about(bookCreateDto.getAbout())
                        .projectId(ResponseUtils.generateBookSerialNumber(10, bookCreateDto.getTitle()))
                        .reviewStatus(ReviewStatus.Pending)
                        .activeStatus(true)
                        .createdOn(LocalDateTime.now())
                .build());

        BookResponseDto bookResponseDto = new BookResponseDto(bookEntity);

        return new ResponsePojo<>(ResponseUtils.CREATED, true,
                "Book has been added.", bookResponseDto );
    }

    @Override
    public ResponseEntity<?> findAllBooks(int pageNum, int pageSize) {

        List<BookEntity> bookEntityList = bookRepository.findAll();
        if (bookEntityList.isEmpty())
            return new ResponseEntity<>("There are no projects currently", HttpStatus.NOT_FOUND);

        List<BookResponseDto> responseDtoList = new ArrayList<>();

        //preparing response dto
        bookEntityList.stream().filter(BookEntity::getActiveStatus).skip(pageNum).limit(pageSize).map(
                BookResponseDto::new
        ).forEach(responseDtoList::add);

        ResponsePojo<List<BookResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "All books found", responseDtoList, pageNum, pageSize, responseDtoList.size());

        return ResponseEntity.ok(responsePojo);
    }

    @Override
    public ResponseEntity<?> findBookByAuthor(String author, int pageNum, int pageSize) {

        List<BookEntity> bookEntityList = bookRepository.findByAuthor(author)
                .orElseThrow(()-> new ApiException("There are no projects by this author: " + author));

        List<BookResponseDto> responseDtoList = new ArrayList<>();

        //preparing response dto
        bookEntityList.stream().filter(BookEntity::getActiveStatus).skip(pageNum).limit(pageSize).map(
                BookResponseDto::new
        ).forEach(responseDtoList::add);

        ResponsePojo<List<BookResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "Books by author", responseDtoList, pageNum, pageSize, responseDtoList.size());

        return ResponseEntity.ok(responsePojo);    }

    @Override
    public ResponseEntity<?> findBookByTitle(String title, int pageNum, int pageSize) {

        List<BookEntity> bookEntityList = bookRepository.findByTitle(title)
                .orElseThrow(()-> new ApiException("There are no projects by this title: " + title));

        List<BookResponseDto> responseDtoList = new ArrayList<>();

        //preparing response dto
        bookEntityList.stream().filter(BookEntity::getActiveStatus).skip(pageNum).limit(pageSize).map(
                BookResponseDto::new
        ).forEach(responseDtoList::add);

        ResponsePojo<List<BookResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "Books by title", responseDtoList, pageNum, pageSize, responseDtoList.size());

        return ResponseEntity.ok(responsePojo);
    }

    @Override
    public ResponseEntity<?> findBookByProjectId(String projectId) {

        BookEntity bookEntity = bookRepository.findByProjectId(projectId)
                .orElseThrow(()-> new ApiException("There are no projects with this project id: " + projectId));

        BookResponseDto bookResponseDto = new BookResponseDto(bookEntity);

        return ResponseEntity.ok(bookResponseDto);
    }

    // TODO: 13/08/2023 To complete the following methods
    @Override
    public ResponseEntity<?> findPendingBookReview(int pageNum, int pageSize) {
        List<BookEntity> bookEntityList = bookRepository.findAll().stream().filter(x-> x.getReviewStatus() == ReviewStatus.Pending)
                .toList();
        List<BookResponseDto> responseDtoList = new ArrayList<>();

        if(bookEntityList.isEmpty())
            return new ResponseEntity<>("There are no pending reviews yet.", HttpStatus.NOT_FOUND);

        bookEntityList.stream().filter(BookEntity::getActiveStatus).skip(pageNum).limit(pageSize)
                .map(
                        BookResponseDto::new
                ).forEach(responseDtoList::add);

        ResponsePojo<List<BookResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "Books with pending review", responseDtoList, pageNum, pageSize, responseDtoList.size());

        return ResponseEntity.ok(responsePojo);
    }

    @Override
    public ResponseEntity<?> findStartedBookReview(int pageNum, int pageSize) {
        List<BookEntity> bookEntityList = bookRepository.findAll().stream().filter(x-> x.getReviewStatus() == ReviewStatus.Started)
                .toList();
        List<BookResponseDto> responseDtoList = new ArrayList<>();

        if(bookEntityList.isEmpty())
            return new ResponseEntity<>("There are no 'started' reviews yet.", HttpStatus.NOT_FOUND);

        bookEntityList.stream().filter(BookEntity::getActiveStatus).skip(pageNum).limit(pageSize)
                .map(
                        BookResponseDto::new
                ).forEach(responseDtoList::add);

        ResponsePojo<List<BookResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "Books with started review", responseDtoList, pageNum, pageSize, responseDtoList.size());

        return ResponseEntity.ok(responsePojo);    }

    @Override
    public ResponseEntity<?> findCompletedBookReview(int pageNum, int pageSize) {
        List<BookEntity> bookEntityList = bookRepository.findAll().stream().filter(x-> x.getReviewStatus() == ReviewStatus.Completed)
                .toList();
        List<BookResponseDto> responseDtoList = new ArrayList<>();

        if(bookEntityList.isEmpty())
            return new ResponseEntity<>("There are no completed reviews yet.", HttpStatus.NOT_FOUND);

        bookEntityList.stream().filter(BookEntity::getActiveStatus).skip(pageNum).limit(pageSize)
                .map(
                        BookResponseDto::new
                ).forEach(responseDtoList::add);

        ResponsePojo<List<BookResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "All books found", responseDtoList, pageNum, pageSize, responseDtoList.size());

        return ResponseEntity.ok(responsePojo);    }

    @Override
    public ResponseEntity<?> updateBook(BookUpdateDto bookUpdateDto) {
        BookEntity bookEntity = bookRepository.findByProjectId(bookUpdateDto.getProjectId())
                .orElseThrow(()-> new ApiException(String.format("Book with projectId: %s does not exist",
                        bookUpdateDto.getProjectId())));

        bookEntity.setCoverImage(bookUpdateDto.getCoverImage());
        bookEntity.setTitle(bookUpdateDto.getTitle());
        bookEntity.setAuthor(bookUpdateDto.getAuthor());
        bookEntity.setActiveStatus(true);
        bookEntity.setReviewStatus(bookUpdateDto.getReviewStatus());
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
                        BookResponseDto::new
                ).forEach(responseDtoList::add);


        ResponsePojo<List<BookResponseDto>> responsePojo =new ResponsePojo<>(ResponseUtils.FOUND, true,
                "All books found", responseDtoList, pageNum, pageSize, responseDtoList.size());

        return ResponseEntity.ok(responsePojo);    }
}
