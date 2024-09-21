package com.maksim.find_worker.listener;

import com.maksim.find_worker.service.JobOfferService;
import com.maksim.find_worker.service.JobPostService;
import com.maksim.find_worker.service.ReviewService;
import javassist.NotFoundException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.*;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class UserServiceListener {

    private  ReviewService reviewService;
    private  JobPostService jobPostService;
    private  MessageHelper messageHelper;
    private JobOfferService jobOfferService;


    public UserServiceListener(ReviewService reviewService,JobOfferService jobOfferService ,JobPostService jobPostService, MessageHelper messageHelper) {
        this.reviewService = reviewService;
        this.jobPostService = jobPostService;
        this.messageHelper = messageHelper;
        this.jobOfferService = jobOfferService;
    }

    @JmsListener(destination = "${destination.deleteReviews}", concurrency = "5-10")
    public void deleteClientReviews(Message message) throws JMSException, NotFoundException {
        Long clientId = messageHelper.getMessage(message, Long.class);
        reviewService.deleteAllReviewsByClient(clientId);
    }

    @JmsListener(destination = "${destination.deleteJobPosts}", concurrency = "5-10")
    public void deleteClientJobPosts(Message message) throws JMSException {
        Long clientId = messageHelper.getMessage(message, Long.class);
        jobPostService.deleteJobPostsByClientId(clientId);
    }

    @JmsListener(destination = "${destination.deleteJobOffers}", concurrency = "5-10")
    public void deleteJobOffers(Message message) throws JMSException {
        Long workerId = messageHelper.getMessage(message, Long.class);
        jobOfferService.deleteJobOffersByWorkerId(workerId);
    }

}
