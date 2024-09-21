package com.maksim.find_worker.service;

import com.maksim.find_worker.dto.JobOfferDto;
import com.maksim.find_worker.dto.JobOfferIn;
import com.maksim.find_worker.dto.JobPostDto;
import com.maksim.find_worker.dto.JobPostIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface JobPostService {
    JobPostDto addJobPost(JobPostIn jobPostIn);
    Page<JobOfferDto> getJobOffersForJobPost(Long jobPostId, Pageable pageable);
    void deleteJobPost(Long jobPostId);
    void deleteJobPostsByClientId(Long clientId);
    JobPostDto getJobPostById(Long jobPostId);
    List<JobPostDto> getAllJobPosts();

}
