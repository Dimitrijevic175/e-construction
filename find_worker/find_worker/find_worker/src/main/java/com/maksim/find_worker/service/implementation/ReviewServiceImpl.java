package com.maksim.find_worker.service.implementation;

import com.maksim.find_worker.domain.ClientDto;
import com.maksim.find_worker.domain.Review;
import com.maksim.find_worker.dto.ReviewDto;
import com.maksim.find_worker.dto.ReviewNotification;
import com.maksim.find_worker.dto.WorkerDto;
import com.maksim.find_worker.listener.MessageHelper;
import com.maksim.find_worker.mapper.ReviewMapper;
import com.maksim.find_worker.repository.ReviewRepository;
import com.maksim.find_worker.service.ReviewService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private ReviewMapper reviewMapper;
    private RestTemplate restTemplate;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;

    private String reviewDestination;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper,
                             JmsTemplate jmsTemplate, MessageHelper messageHelper,
                             @Value("${destination.reviewed}") String reviewDestination, RestTemplate restTemplate) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.restTemplate = restTemplate;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.reviewDestination = reviewDestination;
    }


    @Override
    public ReviewDto addReview(ReviewDto reviewDto) {
        // Check if the user exists
        if (!userExists(reviewDto.getReviewerId())) {
            throw new IllegalArgumentException("User with ID " + reviewDto.getReviewerId() + " does not exist.");
        }

        // Check if the worker exists
        if (!workerExists(reviewDto.getReviewedId())) {
            throw new IllegalArgumentException("Worker with ID " + reviewDto.getReviewedId() + " does not exist.");
        }

        Review review = reviewMapper.reviewDtoToReview(reviewDto);
        Review savedReview = reviewRepository.save(review);

        // Dohvatanje klijenta sa user servisa
        Long clientId = review.getReviewerId();
        String url = "/client/" + clientId;
        ClientDto clientDto = restTemplate.getForObject(url, ClientDto.class);

        // Dohvatanje workera sa user servisa
        Long workerId = review.getReviewedId();
        String url2 = "/worker/" + workerId;
        WorkerDto workerDto = restTemplate.getForObject(url2, WorkerDto.class);

        ReviewNotification notification = new ReviewNotification();
        notification.setName(workerDto.getName());
        notification.setSurname(workerDto.getLast_name());
        notification.setEmail(workerDto.getEmail());
        notification.setClientName(clientDto.getName());
        notification.setClientSurname(clientDto.getLast_name());
        notification.setComment(review.getComment());
        notification.setRating(review.getRating());
        notification.setClientId(clientId);
        notification.setWorkerId(workerId);

        jmsTemplate.convertAndSend(reviewDestination, messageHelper.createTextMessage(notification));


        return reviewMapper.reviewToReviewDto(savedReview);
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        validateUserAndWorker(reviewDto.getReviewerId(), reviewDto.getReviewedId());

        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Review not found"));
        review.setReviewerId(reviewDto.getReviewerId());
        review.setReviewedId(reviewDto.getReviewedId());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setDateReviewed(reviewDto.getDateReviewed());
        reviewRepository.save(review);
        return reviewMapper.reviewToReviewDto(review);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return reviewMapper.reviewToReviewDto(review);
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream().map(reviewMapper::reviewToReviewDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAllReviewsByClient(Long clientId) throws NotFoundException {
        // Proverite da li postoje recenzije za datog klijenta
        if (reviewRepository.findAllByReviewerId(clientId).isEmpty()) {
            throw new NotFoundException("No reviews found for client with id " + clientId);
        }

        // Obrišite sve recenzije za klijenta
        reviewRepository.deleteAllByReviewerId(clientId);
    }

    private void validateUserAndWorker(Long userId, Long workerId) {
        if (!userExists(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }
        if (!workerExists(workerId)) {
            throw new IllegalArgumentException("Worker with ID " + workerId + " does not exist.");
        }
    }

    private boolean userExists(Long userId) {
        try {
            restTemplate.getForObject("/client/" + userId, Void.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

    private boolean workerExists(Long workerId) {
        try {
            restTemplate.getForObject("/worker/" + workerId, Void.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
