package com.maksim.notification_service.listener;

import com.maksim.notification_service.dto.JobOfferedNotification;
import com.maksim.notification_service.dto.OfferAcceptedNotification;
import com.maksim.notification_service.dto.RegisterNotification;
import com.maksim.notification_service.dto.ReviewNotification;
import com.maksim.notification_service.service.EmailService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.mail.MessagingException;

@Component
public class EmailListener {

    private MessageHelper messageHelper;
    private EmailService emailService;

    public EmailListener(MessageHelper messageHelper, EmailService emailService) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
    }

    @JmsListener(destination = "${destination.register}", concurrency = "5-10")
    public void register(Message message) throws JMSException {
        RegisterNotification registerNotification = messageHelper.getMessage(message, RegisterNotification.class);
        emailService.sendSimpleMessage(registerNotification.getEmail(), "Activate Your Account!", registerNotification.toString(), registerNotification.getReceiverId());
//        try {
//            emailService.sendHtmlMessage(registerNotification.getEmail(),"Activate Your Account!",registerNotification.toString());
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            // Rukovanje greškama
//        }
        System.out.println("Sent on email: " + registerNotification.getEmail());
    }

    @JmsListener(destination = "${destination.offerAccepted}", concurrency = "5-10")
    public void offerAccepted(Message message) throws JMSException {
        OfferAcceptedNotification notification = messageHelper.getMessage(message, OfferAcceptedNotification.class);
        emailService.sendSimpleMessage(notification.getEmail(), "PONUDA PRIHVACENA !",notification.toString(), notification.getClientId());
    }

    @JmsListener(destination = "${destination.jobOffered}", concurrency = "5-10")
    public void jobOffered(Message message) throws JMSException {
        JobOfferedNotification notification = messageHelper.getMessage(message, JobOfferedNotification.class);
        emailService.sendSimpleMessage(notification.getEmail(), "DOBILI STE PONUDU ZA POSAO !", notification.toString(),notification.getClientId());
    }

    @JmsListener(destination = "${destination.reviewed}", concurrency = "5-10")
    public void reviewed(Message message) throws JMSException {
        ReviewNotification notification = messageHelper.getMessage(message,ReviewNotification.class);
        emailService.sendSimpleMessage(notification.getEmail(), "DOBILI STE RECENZIJU !", notification.toString(),1L);
    }

}
