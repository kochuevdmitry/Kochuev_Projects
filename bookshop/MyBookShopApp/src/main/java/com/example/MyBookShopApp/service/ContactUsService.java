package com.example.MyBookShopApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactUsService {

    private final JavaMailSender javaMailSender;

    @Value("${appEmail.email}")
    private String emailFrom;

    @Autowired
    public ContactUsService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public Boolean handleContactUsForm(String name, String senderEmail, String topic, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(emailFrom);

        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText("Сообщение от: " + name + ", почтовый адрес отправителя: " + senderEmail +
                "\n Текст сообщения: \n" + message);
        javaMailSender.send(simpleMailMessage);
        return true;
    }
}
