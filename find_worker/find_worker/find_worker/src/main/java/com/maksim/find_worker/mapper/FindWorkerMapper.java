package com.maksim.find_worker.mapper;

import com.maksim.find_worker.domain.*;
import com.maksim.find_worker.dto.JobPostDto;
import com.maksim.find_worker.dto.ReviewDto;
import org.springframework.stereotype.Component;

@Component
public class FindWorkerMapper {


    // Mapiranje JobPost entiteta na JobPostDto
    public JobPostDto jobPosttoJobPostDto(JobPost jobPost) {
        if (jobPost == null) {
            return null;
        }
        JobPostDto dto = new JobPostDto();
        dto.setId(jobPost.getId());
        dto.setTitle(jobPost.getTitle());
        dto.setDescription(jobPost.getDescription());
        dto.setDate_posted(jobPost.getDatePosted());
        dto.setClient_id(jobPost.getClientId());
        return dto;
    }

    // Mapiranje JobPostDto na JobPost entitet
    public JobPost jobPostDtotoJobPost(JobPostDto dto) {
        if (dto == null) {
            return null;
        }
        JobPost jobPost = new JobPost();
        jobPost.setId(dto.getId());
        jobPost.setTitle(dto.getTitle());
        jobPost.setDescription(dto.getDescription());
        jobPost.setDatePosted(dto.getDate_posted());
        jobPost.setClientId(dto.getClient_id());
        return jobPost;
    }


    // Mapiranje Review entiteta na ReviewDto
    public ReviewDto toReviewDto(Review review) {
        if (review == null) {
            return null;
        }
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setReviewerId(review.getReviewerId());
        dto.setReviewedId(review.getReviewedId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setDateReviewed(review.getDateReviewed());
        return dto;
    }

    // Mapiranje ReviewDto na Review entitet
    public Review toReview(ReviewDto dto) {
        if (dto == null) {
            return null;
        }
        Review review = new Review();
        review.setId(dto.getId());
        review.setReviewerId(dto.getReviewerId());
        review.setReviewedId(dto.getReviewedId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setDateReviewed(dto.getDateReviewed());
        return review;
    }

}
