package com.maksim.find_worker.mapper;

import com.maksim.find_worker.domain.Review;
import com.maksim.find_worker.domain.ReviewedType;
import com.maksim.find_worker.domain.ReviewerType;
import com.maksim.find_worker.dto.*;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDto reviewToReviewDto(Review review) {
        if (review == null) {
            return null;
        }

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setReviewerId(review.getReviewerId());
        reviewDto.setReviewedId(review.getReviewedId());
        reviewDto.setRating(review.getRating());
        reviewDto.setComment(review.getComment());
        reviewDto.setDateReviewed(review.getDateReviewed());

        return reviewDto;
    }

    public Review reviewDtoToReview(ReviewDto reviewDto) {
        if (reviewDto == null) {
            return null;
        }

        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setReviewerId(reviewDto.getReviewerId());
        review.setReviewedId(reviewDto.getReviewedId());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setDateReviewed(reviewDto.getDateReviewed());

        return review;
    }

}
