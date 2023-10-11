package com.akinnova.BookReviewGrad.service.ratingservice;

import com.akinnova.BookReviewGrad.dto.ratingdto.RatingDto;
import com.akinnova.BookReviewGrad.entity.Rating;
import com.akinnova.BookReviewGrad.response.ResponseType;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.RatingRepository;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements IRatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> rateProvider(RatingDto rateDto) {
        //Checks if username is in user repository
        userRepository.findByUsername(rateDto.getUsername())
                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, rateDto.getUsername())));

        if(rateDto.getStarRating() > 5 || rateDto.getStarRating()<1){
            return new ResponseEntity<>("Value given is invalid. Choose a value between 1 and 5", HttpStatus.ACCEPTED);
        }
        Rating rating = ratingRepository.findByUsername(rateDto.getUsername())
                .orElse(ratingRepository.save(Rating.builder()
                        .username(rateDto.getUsername())
                                .starRating(rateDto.getStarRating())
                                .rateCount((long)1)
                                .averageRating((double)rateDto.getStarRating())
                                .rateTime(LocalDateTime.now())
                        .build()));
//        Rating userRate = ratingRepository.findAll().stream().filter(x-> x.getUsername().getUsername().equals(rateDto.getUsername()))
//                .findFirst().orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, rateDto.getUsername())));
        //If user has reviewed a book before, retrieve the exact record and edit


//        Rating userRate =ratingRepository.findByUsername(rateDto.getUsername())
//                .orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, rateDto.getUsername())));

//        //Create a new rating for a user that has not been rated before
//        if (userRate.getStarRating() == null){
//            ratingRepository.save(Rating.builder()
//                    .username(userRate.getUsername())
//                    .starRating(rateDto.getStarRating())
//                    .rateCount((long)1)
//                    .averageRating((double)rateDto.getStarRating())
//                    .rateTime(LocalDateTime.now())
//                    .build());
//        }

        //obtains the current average rating
        double currentAverage = rating.getAverageRating();
        //obtains the current rate counts (i.e. number of rates given)
        long currentCount = rating.getRateCount();

        rating.setStarRating(rateDto.getStarRating());
        rating.setRateCount(rating.getRateCount() + 1);
        //To calculate the new average: Product of former average and former count summed with new rating and all
        //...divided with new count (i.e. former count + 1
        rating.setAverageRating(((currentAverage * currentCount) + rateDto.getStarRating())
                / (currentCount + 1));
        //Save update into the database
        ratingRepository.save(rating);


        return new ResponseEntity<>("Thanks for your review", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<?> serviceProviderRates(String username) {

        //To retrieve all reviews for a book by title
//        Rating rateBook = ratingRepository.findByUsername(username)
//                .orElseThrow(()->
//                        new ApiException(String.format("There are no reviews for user:  %s yet", username)));

        Rating rate = ratingRepository.findAll().stream().filter(x-> x.getUsername().equals(username))
                .findFirst().orElseThrow(()-> new ApiException(String.format(ResponseUtils.NO_USER_BY_USERNAME, username)));

        return new ResponseEntity<>(String.format("Rate for book title %s: %f from %d users", username, rate.getAverageRating(),
                rate.getRateCount() ), HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?> allRates(int pageNum, int pageSize) {
        //To retrieve all reviews in the review database
        List<Rating> rateBookList = ratingRepository.findAll().stream().skip(pageNum - 1).limit(pageSize).toList();

        if(rateBookList.isEmpty())
            return new ResponseEntity<>("There are no reviews yet", HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All rates", rateBookList));
    }
}
