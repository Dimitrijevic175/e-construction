package com.maksim.find_worker.service;

import com.maksim.find_worker.dto.*;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface ReviewService {

    ReviewDto addReview(ReviewDto reviewDto);

    ReviewDto updateReview(Long id, ReviewDto reviewDto);

    void deleteReview(Long id);

    ReviewDto getReviewById(Long id);

    List<ReviewDto> getAllReviews();
    void deleteAllReviewsByClient(Long clientId) throws NotFoundException;

}
