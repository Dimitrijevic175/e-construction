package com.maksim.find_worker.controller;

import com.maksim.find_worker.dto.*;
import com.maksim.find_worker.service.JobPostService;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobPosts")
public class JobPostController {

    private JobPostService jobPostService;

    @GetMapping("/{id}")
    public ResponseEntity<JobPostDto> getJobPostById(@PathVariable Long id) {
        JobPostDto jobPostDto = jobPostService.getJobPostById(id);
        if (jobPostDto != null) {
            return ResponseEntity.ok(jobPostDto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Konstruktor-based Dependency Injection
    public JobPostController(JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }

    // Endpoint za kreiranje novog JobPost-a
    @PostMapping
    public ResponseEntity<JobPostDto> createJobPost(@RequestBody JobPostIn jobPostIn) {
        JobPostDto jobPostDto = jobPostService.addJobPost(jobPostIn);
        return new ResponseEntity<>(jobPostDto, HttpStatus.CREATED);
    }

    @GetMapping("/jobOffers/{jobPostId}")
    public ResponseEntity<Page<JobOfferDto>> getJobOffersForJobPost(
            @PathVariable Long jobPostId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<JobOfferDto> jobOffers = jobPostService.getJobOffersForJobPost(jobPostId, pageable);
        return ResponseEntity.ok(jobOffers);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPost(@PathVariable Long id) {
        jobPostService.deleteJobPost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/client/{clientId}")
    public ResponseEntity<Void> deleteJobPostsByClientId(@PathVariable Long clientId) {
        jobPostService.deleteJobPostsByClientId(clientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobPostDto>> getAllJobPosts() {
        List<JobPostDto> jobPosts = jobPostService.getAllJobPosts();
        return ResponseEntity.ok(jobPosts);
    }

}
