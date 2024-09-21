package com.maksim.find_worker.service.implementation;

import com.maksim.find_worker.domain.ClientDto;
import com.maksim.find_worker.domain.JobOffer;
import com.maksim.find_worker.dto.*;
import com.maksim.find_worker.listener.MessageHelper;
import com.maksim.find_worker.mapper.FindWorkerMapper;
import com.maksim.find_worker.mapper.JobOfferMapper;
import com.maksim.find_worker.repository.JobOfferRepository;
import com.maksim.find_worker.service.JobOfferService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Optional;

@Service
@Transactional
public class JobOfferServiceImpl implements JobOfferService {

    private JobOfferRepository jobOfferRepository;
    private JobOfferMapper mapper;
    private RestTemplate restTemplate;
    private JmsTemplate jmsTemplate;
    private String acceptedDestination;
    private MessageHelper messageHelper;
    private String jobOfferedDestination;


    public JobOfferServiceImpl(JobOfferRepository jobOfferRepository, JmsTemplate jmsTemplate,
                               @Value("${destination.offerAccepted}") String acceptedDestination,
                               MessageHelper messageHelper,JobOfferMapper mapper, RestTemplate restTemplate,
                               @Value("${destination.jobOffered}") String jobOfferedDestination) {
        this.jobOfferRepository = jobOfferRepository;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.jmsTemplate = jmsTemplate;
        this.acceptedDestination = acceptedDestination;
        this.messageHelper = messageHelper;
        this.jobOfferedDestination = jobOfferedDestination;
    }

    @Override
    public Page<JobOfferDto> findJobOffersByWorkerId(Long workerId, Pageable pageable) {
        // Korak 1: Pretraži entitete pomoću repository-ja
        Page<JobOffer> jobOffersPage = jobOfferRepository.findByWorkerId(workerId, pageable);

        // Korak 2: Mapiraj rezultate iz entiteta u DTO
        return jobOffersPage.map(mapper::jobOffertoJobOfferDto);
    }
    @Override
    public Page<JobOfferDto> findAllJobOffers(Pageable pageable) {
        // Korak 1: Pretraži sve JobOffer entitete koristeći findAll metodu iz repository-ja
        Page<JobOffer> jobOffersPage = jobOfferRepository.findAll(pageable);

        // Korak 2: Mapiraj rezultate iz entiteta u DTO
        return jobOffersPage.map(mapper::jobOffertoJobOfferDto);
    }

    @Override
    public JobOfferDto findJobOfferById(Long id) {
        // Korak 1: Pronađi JobOffer entitet koristeći ID
        // Vraća Optional<JobOffer> koji može biti prazan ako entitet nije pronađen
        return jobOfferRepository.findById(id)
                .map(mapper::jobOffertoJobOfferDto) // Ako entitet postoji, mapiraj ga na DTO
                .orElse(null); // Ako entitet ne postoji, vrati null
    }

    @Override
    public JobOfferDto addJobOffer(JobOfferIn jobOfferIn) {
        // Mapiranje JobOfferIn u JobOffer entitet
        JobOffer jobOffer = mapper.jobOfferInToJobOffer(jobOfferIn);

        // Čuvanje entiteta u bazi podataka
        jobOffer = jobOfferRepository.save(jobOffer);

        // Postavljanje URI šeme za JobPostService poziv
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081/api/jobPosts"));


        // Dohvatanje jobPosta
        Long jobPostId = jobOffer.getJobPost().getId();
        String url = "/" + jobPostId;
        JobPostDto jobPostDto = restTemplate.getForObject(url, JobPostDto.class);

        // Postavljanje URI šeme za UserService poziv
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080/api/user"));

        // Dohvatanje klijenta sa user servisa
        Long clientId = jobPostDto.getClient_id();
        String clientUrl = "/client/" + clientId;
        ClientDto clientDto = restTemplate.getForObject(clientUrl, ClientDto.class);



        JobOfferedNotification notification = new JobOfferedNotification();
        notification.setEmail(clientDto.getEmail());
        notification.setName(clientDto.getName());
        notification.setSurname(clientDto.getLast_name());
        notification.setWorkerName(jobOffer.getName());
        notification.setWorkerSurname(jobOffer.getLastName());
        notification.setOfferDetails(jobOffer.getOfferDetails());
        notification.setStartingPrice(jobOffer.getStartingPrice());
        notification.setClientId(clientId);

        jmsTemplate.convertAndSend(jobOfferedDestination,messageHelper.createTextMessage(notification));

        // Mapiranje nazad u JobOfferDto
        return mapper.jobOffertoJobOfferDto(jobOffer);
    }

    @Override
    public JobOfferDto setJobOfferAccepted(Long jobOfferId, boolean accepted) {
        Optional<JobOffer> optionalJobOffer = jobOfferRepository.findById(jobOfferId);
        if (optionalJobOffer.isPresent()) {
            JobOffer jobOffer = optionalJobOffer.get();
            jobOffer.setAccepted(accepted);
            jobOffer = jobOfferRepository.save(jobOffer);



            Long workerId = jobOffer.getWorkerId();
            String url = "/worker/" + workerId;
            WorkerDto workerDto = restTemplate.getForObject(url, WorkerDto.class);



            OfferAcceptedNotification notification = new OfferAcceptedNotification();
            notification.setName(jobOffer.getName());
            notification.setLastName(jobOffer.getLastName());
            notification.setEmail(jobOffer.getEmail());
            notification.setClientId(workerId);

            if (workerDto != null) {
                notification.setClientName(workerDto.getName());
                notification.setClientSurname(workerDto.getLast_name());
            }


            restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081/api/jobPosts"));



            Long jobPostId = jobOffer.getJobPost().getId();
            String url2 = "/" + jobPostId;
            JobPostDto jobPostDto = restTemplate.getForObject(url2, JobPostDto.class);

            notification.setTitle(jobPostDto.getTitle());
            notification.setDescription(jobPostDto.getDescription());


            jmsTemplate.convertAndSend(acceptedDestination, messageHelper.createTextMessage(notification));

            return mapper.jobOffertoJobOfferDto(jobOffer);
        } else {
            throw new RuntimeException("Job offer not found");
        }
    }

    @Override
    public void deleteJobOffer(Long jobOfferId) {
        if (!jobOfferRepository.existsById(jobOfferId)) {
            throw new IllegalArgumentException("JobOffer with ID " + jobOfferId + " does not exist.");
        }
        jobOfferRepository.deleteById(jobOfferId);
    }

    @Override
    public void deleteJobOffersByWorkerId(Long workerId) {
        Page<JobOffer> jobOffers = jobOfferRepository.findByWorkerId(workerId, Pageable.unpaged());
        for (JobOffer jobOffer : jobOffers) {
            jobOfferRepository.delete(jobOffer);
        }
    }


}
