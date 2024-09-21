package com.maksim.find_worker.service;

import com.maksim.find_worker.dto.JobOfferDto;
import com.maksim.find_worker.dto.JobOfferIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface JobOfferService {
    Page<JobOfferDto> findJobOffersByWorkerId(Long workerId, Pageable pageable);
    Page<JobOfferDto> findAllJobOffers(Pageable pageable);
    JobOfferDto findJobOfferById(Long id);
    JobOfferDto addJobOffer(JobOfferIn jobOfferIn);
    JobOfferDto setJobOfferAccepted(Long jobOfferId, boolean accepted);
    void deleteJobOffer(Long jobOfferId);
    void deleteJobOffersByWorkerId(Long workerId);

}
