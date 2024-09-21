package com.maksim.find_worker.repository;

import com.maksim.find_worker.domain.JobOffer;
import com.maksim.find_worker.dto.JobOfferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    Page<JobOffer> findByWorkerId(Long workerId, Pageable pageable);
    Page<JobOffer> findByJobPostId(Long jobPostId, Pageable pageable);


}
