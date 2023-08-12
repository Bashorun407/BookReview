package com.akinnova.BookReviewGrad.service.ratingservice;

import com.akinnova.BookReviewGrad.dto.ratingdto.RatingDto;
import org.springframework.http.ResponseEntity;

public interface IRatingService {
    ResponseEntity<?> rateBook(RatingDto rateDto);
    ResponseEntity<?> titleRates(String title);
    ResponseEntity<?> allRates(int pageNum, int pageSize);
}
