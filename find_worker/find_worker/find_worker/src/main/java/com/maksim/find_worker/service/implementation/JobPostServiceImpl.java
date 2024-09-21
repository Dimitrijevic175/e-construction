package com.maksim.find_worker.service.implementation;

import com.maksim.find_worker.domain.JobOffer;
import com.maksim.find_worker.domain.JobPost;
import com.maksim.find_worker.dto.JobPostDto;
import com.maksim.find_worker.dto.*;
import com.maksim.find_worker.mapper.JobOfferMapper;
import com.maksim.find_worker.mapper.JobPostMapper;
import com.maksim.find_worker.repository.JobOfferRepository;
import com.maksim.find_worker.repository.JobPostRepository;
import com.maksim.find_worker.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobPostServiceImpl implements JobPostService {

    private JobPostRepository jobPostRepository;
    private JobPostMapper jobPostMapper;
    private JobOfferRepository jobOfferRepository;
    private JobOfferMapper jobOfferMapper;

    public JobPostServiceImpl(JobPostRepository jobPostRepository,JobOfferMapper jobOfferMapper,JobOfferRepository jobOfferRepository ,JobPostMapper jobPostMapper) {
        this.jobPostRepository = jobPostRepository;
        this.jobPostMapper = jobPostMapper;
        this.jobOfferRepository = jobOfferRepository;
        this.jobOfferMapper = jobOfferMapper;
    }

    @Override
    public JobPostDto addJobPost(JobPostIn jobPostIn) {

        // Pretvaranje JobPostIn DTO-a u JobPost entitet
        JobPost jobPost = new JobPost();
        jobPost.setTitle(jobPostIn.getTitle());
        jobPost.setDescription(jobPostIn.getDescription());
        jobPost.setDatePosted(jobPostIn.getDate_posted()); // Pretpostavljam da je ovo ispravno ime za datumsko polje
        jobPost.setClientId(jobPostIn.getClient_id()); // Pretpostavljam da je ovo ispravno ime za ID klijenta

        // Čuvanje entiteta u bazu
        JobPost savedJobPost = jobPostRepository.save(jobPost);

        // Pretvaranje sačuvanog entiteta u JobPostDto
        return jobPostMapper.jobPostToJobPostDto(savedJobPost);
    }

    @Override
    public Page<JobOfferDto> getJobOffersForJobPost(Long jobPostId, Pageable pageable) {
        Page<JobOffer> jobOffers = jobOfferRepository.findByJobPostId(jobPostId,pageable);
        return jobOffers.map(jobOfferMapper::jobOffertoJobOfferDto);
    }

    @Override
    public void deleteJobPost(Long jobPostId) {
        if (!jobPostRepository.existsById(jobPostId)) {
            throw new IllegalArgumentException("JobPost with ID " + jobPostId + " does not exist.");
        }
        jobPostRepository.deleteById(jobPostId);
    }

    @Override
    public void deleteJobPostsByClientId(Long clientId) {
        List<JobPost> jobPosts = jobPostRepository.findByClientId(clientId);
        jobPostRepository.deleteAll(jobPosts);
    }

    @Override
    public JobPostDto getJobPostById(Long jobPostId) {
        return jobPostRepository.findById(jobPostId)
                .map(jobPostMapper::jobPostToJobPostDto)
                .orElse(null);
    }

    @Override
    public List<JobPostDto> getAllJobPosts() {
        return jobPostRepository.findAll()
                .stream()
                .map(jobPostMapper::jobPostToJobPostDto)  // Convert each JobPost entity to JobPostDto
                .collect(Collectors.toList());
    }

}
