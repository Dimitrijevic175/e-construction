package com.maksim.find_worker.dto;

import java.time.LocalDate;

public class ReviewDto {
    private Long id;
    private Long reviewerId; // Reference to the reviewer (User or Worker)
    private Long reviewedId; // Reference to the reviewed entity (User or Worker)
    private double rating;
    private String comment;
    private LocalDate dateReviewed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getReviewedId() {
        return reviewedId;
    }

    public void setReviewedId(Long reviewedId) {
        this.reviewedId = reviewedId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDateReviewed() {
        return dateReviewed;
    }

    public void setDateReviewed(LocalDate dateReviewed) {
        this.dateReviewed = dateReviewed;
    }
}
