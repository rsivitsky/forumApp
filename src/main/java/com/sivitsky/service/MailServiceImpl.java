package com.sivitsky.service;

import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private MailSender mailSender;

    @Value("${sec.api-key}")
    private String API_KEY;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String from, String to, String subject, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }

    public void sendMailWithSparkPost(String from, String to, String subject, String msg, String head) throws SparkPostException {

        SparkPostClient client = new SparkPostClient(API_KEY);
        client.sendMessage(from, to,
                subject,
                msg,
                head);
    }

}
