package com.maksim.notification_service.service;


import com.maksim.notification_service.domain.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
public class EmailService {
    @Autowired
    public JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text, Long userId) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("maksimdimitrijevic@gmail.com");


        Mail mail = new Mail();
        mail.setEmail(to);
        mail.setContent(text);
        mail.setTimeofsending(java.time.LocalDate.now());
        mail.setReceiverId(userId);


        mailSender.send(message);
    }

    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); // Drugi parametar true označava da je sadržaj HTML

        mailSender.send(message);
    }


}
