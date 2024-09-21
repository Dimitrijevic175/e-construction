package com.maksim.find_worker.mapper;

import com.maksim.find_worker.domain.JobOffer;
import com.maksim.find_worker.domain.JobPost;
import com.maksim.find_worker.dto.JobOfferDto;
import com.maksim.find_worker.dto.JobOfferIn;
import org.springframework.stereotype.Component;

@Component
public class JobOfferMapper {

    // Mapiranje JobOfferIn na JobOffer
    public JobOffer jobOfferInToJobOffer(JobOfferIn jobOfferIn) {
        if (jobOfferIn == null) {
            return null;
        }

        JobOffer jobOffer = new JobOffer();

        jobOffer.setJobPost(new JobPost());
        jobOffer.getJobPost().setId(jobOfferIn.getJob_post_id());

        jobOffer.setWorkerId(jobOfferIn.getWorker_id());
        jobOffer.setDateOffered(jobOfferIn.getDate_offered());
        jobOffer.setOfferDetails(jobOfferIn.getOffer_details());
        jobOffer.setName(jobOfferIn.getName());
        jobOffer.setLastName(jobOfferIn.getLast_name());
        jobOffer.setProfession(jobOfferIn.getProfession());
        jobOffer.setEmail(jobOfferIn.getEmail());
        jobOffer.setStartingPrice(jobOfferIn.getStarting_price());
        jobOffer.setAccepted(false); // Po defaultu novi posao nije prihvaćen

        return jobOffer;
    }

    // Mapiranje JobOfferIn na JobOfferDto
    public JobOfferDto jobOfferInToJobOfferDto(JobOfferIn jobOfferIn) {
        if (jobOfferIn == null) {
            return null;
        }

        JobOfferDto dto = new JobOfferDto();
        dto.setJob_post_id(jobOfferIn.getJob_post_id());
        dto.setWorker_id(jobOfferIn.getWorker_id());
        dto.setDate_offered(jobOfferIn.getDate_offered());
        dto.setOffer_details(jobOfferIn.getOffer_details());
        dto.setName(jobOfferIn.getName());
        dto.setLast_name(jobOfferIn.getLast_name());
        dto.setProfession(jobOfferIn.getProfession());
        dto.setEmail(jobOfferIn.getEmail());
        dto.setStarting_price(jobOfferIn.getStarting_price());
        dto.setAccepted(false); // Po defaultu novi posao nije prihvaćen

        return dto;
    }

    // Mapiranje JobOffer entiteta na JobOfferDto
    public JobOfferDto jobOffertoJobOfferDto(JobOffer jobOffer) {
        if (jobOffer == null) {
            return null;
        }
        JobOfferDto dto = new JobOfferDto();
        dto.setId(jobOffer.getId());

        dto.setJob_post_id(jobOffer.getJobPost().getId());
        dto.setWorker_id(jobOffer.getWorkerId());

        dto.setDate_offered(jobOffer.getDateOffered());
        dto.setOffer_details(jobOffer.getOfferDetails());
        dto.setName(jobOffer.getName());
        dto.setLast_name(jobOffer.getLastName());
        dto.setProfession(jobOffer.getProfession());
        dto.setEmail(jobOffer.getEmail());
        dto.setStarting_price(jobOffer.getStartingPrice());
        dto.setAccepted(jobOffer.isAccepted());
        return dto;
    }

    // Mapiranje JobOfferDto na JobOffer entitet
    public JobOffer jobOfferDtotoJobOffer(JobOfferDto dto) {
        if (dto == null) {
            return null;
        }
        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(dto.getId());

        jobOffer.setJobPost(new JobPost()); // Only set the ID to avoid loading the entire JobPost object
        jobOffer.getJobPost().setId(dto.getJob_post_id());

        jobOffer.setWorkerId(dto.getWorker_id());
        jobOffer.setDateOffered(dto.getDate_offered());
        jobOffer.setOfferDetails(dto.getOffer_details());
        jobOffer.setName(dto.getName());
        jobOffer.setLastName(dto.getLast_name());
        jobOffer.setProfession(dto.getProfession());
        jobOffer.setEmail(dto.getEmail());
        jobOffer.setStartingPrice(dto.getStarting_price());
        jobOffer.setAccepted(dto.isAccepted());
        return jobOffer;
    }

}
