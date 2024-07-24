package com.example.Triveni.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    public void mailWithAttachment(String filePath) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setSubject("List of products about to expire in next 30 Days");
        mimeMessageHelper.setFrom(mailProperties.getUsername());
        mimeMessageHelper.setText("Product List");
        mimeMessageHelper.setTo("kalyancharry28@gmail.com");

        FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
        mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), fileSystemResource);

        javaMailSender.send(mimeMessage);

        System.out.println("Mail Send Successfully");
    }
}
