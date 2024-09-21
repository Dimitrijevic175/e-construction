package com.maksim.find_worker.repository;

import com.maksim.find_worker.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByReviewerId(Long reviewerId, Pageable pageable);
    Page<Review> findByReviewedId(Long reviewedId, Pageable pageable);

    List<Review> findAllByReviewerId(Long clientId);

    void deleteAllByReviewerId(Long clientId);
}
