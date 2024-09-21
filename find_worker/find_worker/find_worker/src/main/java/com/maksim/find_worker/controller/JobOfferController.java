package com.maksim.find_worker.controller;

import com.maksim.find_worker.dto.JobOfferDto;
import com.maksim.find_worker.dto.JobOfferIn;
import com.maksim.find_worker.service.JobOfferService;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobOffers")
public class JobOfferController {

    private final JobOfferService jobOfferService;

    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @PostMapping
    public ResponseEntity<JobOfferDto> addJobOffer(@RequestBody JobOfferIn jobOfferIn) {
        JobOfferDto jobOfferDto = jobOfferService.addJobOffer(jobOfferIn);
        return ResponseEntity.ok(jobOfferDto);
    }

    /**
     * Endpoint za preuzimanje ponuda za posao na osnovu workerId.
     *
     * @param workerId ID radnika za kojeg pretražujemo ponude za posao.
     * @param pageable objekat koji sadrži informacije o stranici (npr. broj stranice i veličina stranice).
     * @return stranica ponuda za posao (JobOfferDto) za zadatog radnika.
     */

    @GetMapping("/worker/{workerId}")
    public Page<JobOfferDto> getJobOffersByWorkerId(@PathVariable Long workerId, Pageable pageable) {
        return jobOfferService.findJobOffersByWorkerId(workerId, pageable);
    }

    @GetMapping
    public Page<JobOfferDto> getAllJobOffers(Pageable pageable) {
        return jobOfferService.findAllJobOffers(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOfferDto> getJobOfferById(@PathVariable("id") Long id) {
        // Poziv metode servisa da pronađe JobOffer po ID-u
        JobOfferDto jobOfferDto = jobOfferService.findJobOfferById(id);

        // Proveri da li je JobOfferDto null
        if (jobOfferDto == null) {
            // Ako nije pronađena, vraćamo HTTP 404 Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Ako je pronađena, vraćamo HTTP 200 OK sa JobOfferDto
        return new ResponseEntity<>(jobOfferDto, HttpStatus.OK);
    }

    @PutMapping("/{jobOfferId}/{accepted}")
    public ResponseEntity<JobOfferDto> setJobOfferAccepted(
            @PathVariable Long jobOfferId,
            @PathVariable boolean accepted) {
        JobOfferDto jobOfferDto = jobOfferService.setJobOfferAccepted(jobOfferId, accepted);
        return ResponseEntity.ok(jobOfferDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOffer(@PathVariable Long id) {
        jobOfferService.deleteJobOffer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/worker/{workerId}")
    public ResponseEntity<Void> deleteJobOffersByWorkerId(@PathVariable Long workerId) {
        jobOfferService.deleteJobOffersByWorkerId(workerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
