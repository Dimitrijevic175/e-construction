package com.maksim.find_worker.mapper;

import com.maksim.find_worker.domain.JobPost;
import com.maksim.find_worker.dto.*;
import org.springframework.stereotype.Component;

@Component
public class JobPostMapper {

    // Metod za konverziju iz JobPost entiteta u JobPostDto
    public JobPostDto jobPostToJobPostDto(JobPost jobPost) {
        if (jobPost == null) {
            return null;
        }

        JobPostDto jobPostDto = new JobPostDto();
        jobPostDto.setId(jobPost.getId()); // Pretpostavljam da JobPost entitet ima getId() metodu
        jobPostDto.setTitle(jobPost.getTitle());
        jobPostDto.setDescription(jobPost.getDescription());
        jobPostDto.setDate_posted(jobPost.getDatePosted()); // Koristi getDatePosted() metodu iz JobPost entiteta
        jobPostDto.setClient_id(jobPost.getClientId()); // Koristi getClientId() metodu iz JobPost entiteta

        return jobPostDto;
    }

    // Metod za konverziju iz JobPostIn u JobPostDto
    public JobPostDto jobPostInToJobPostDto(JobPostIn jobPostIn) {
        if (jobPostIn == null) {
            return null;
        }

        JobPostDto jobPostDto = new JobPostDto();
        jobPostDto.setTitle(jobPostIn.getTitle());
        jobPostDto.setDescription(jobPostIn.getDescription());
        jobPostDto.setDate_posted(jobPostIn.getDate_posted());
        jobPostDto.setClient_id(jobPostIn.getClient_id());

        return jobPostDto;
    }

    // Metod za konverziju iz JobPostDto u JobPostIn
    public JobPostIn JobPostDtoToJobPostIn(JobPostDto jobPostDto) {
        if (jobPostDto == null) {
            return null;
        }

        JobPostIn jobPostIn = new JobPostIn();
        jobPostIn.setTitle(jobPostDto.getTitle());
        jobPostIn.setDescription(jobPostDto.getDescription());
        jobPostIn.setDate_posted(jobPostDto.getDate_posted());
        jobPostIn.setClient_id(jobPostDto.getClient_id());

        return jobPostIn;
    }

}
