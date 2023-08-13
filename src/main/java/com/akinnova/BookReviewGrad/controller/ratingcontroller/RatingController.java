package com.akinnova.BookReviewGrad.controller.ratingcontroller;

import com.akinnova.BookReviewGrad.dto.ratingdto.RatingDto;
import com.akinnova.BookReviewGrad.service.ratingservice.RatingServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rates/auth")
public class RatingController {

    private final RatingServiceImpl ratingService;

    public RatingController(RatingServiceImpl ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/rate")
    public ResponseEntity<?> rateBook(@RequestBody RatingDto rateDto) {
        return ratingService.rateBook(rateDto);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> titleRates(@PathVariable String title) {
        return ratingService.titleRates(title);
    }

    @GetMapping("/allRates")
    public ResponseEntity<?> allRates(@RequestParam(defaultValue = "1") int pageNum,
                                      @RequestParam(defaultValue = "20") int pageSize) {
        return ratingService.allRates(pageNum, pageSize);
    }
}
