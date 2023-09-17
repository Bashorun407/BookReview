package com.akinnova.BookReviewGrad.dto.ratingdto;

import lombok.Data;

@Data
public class RatingDto {
    private String username;
    private Integer starRating;

    public RatingDto(String username, Integer starRating) {
        this.username = username;
        this.starRating = starRating;
    }

    public RatingDto(){}
}
