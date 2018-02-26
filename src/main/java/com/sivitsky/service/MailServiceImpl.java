package com.sivitsky.service;

import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private MailSender mailSender;

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
        //String API_KEY = "0b04efb4cf55eaf3ee8b343dfb13e0662b0e774b";
        String API_KEY = "483bc582e6832dadc5b73f0cf10907dc3a107c1d";
        SparkPostClient client = new SparkPostClient(API_KEY);
       /* SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);*/
        client.sendMessage(from, to,
                subject,
                msg,
                head);
    }
     /*   Client sparky = new Client(API_KEY);

        sparky.sendMessage(
                from,
                "renek77@mail.ru",
                "Oh hey!",
                "Testing SparkPost - the world's most awesomest email service!",
                "<p>Testing SparkPost - the world's most awesomest email service!</p>");
    }*/
}
