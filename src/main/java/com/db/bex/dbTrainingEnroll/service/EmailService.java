package com.db.bex.dbTrainingEnroll.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class EmailService {

    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailToUsers(List<String> receivers, @NotEmpty String text, @NotEmpty String subject) throws MessagingException {
       MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        for(String s : receivers)
            System.out.println(s);
        if(receivers.size() > 0) {
            helper.setTo(receivers.get(0));
            helper.setSubject(subject);
            if (receivers.size() > 1) {
                receivers.remove(0);
                helper.setBcc(receivers.toArray(new String[receivers.size()]));
            }
            helper.setText(text);
            mailSender.send(mimeMessage);
        }
    }

    public void sendEmailToManager(@NotEmpty String manager,@NotEmpty String text, @NotEmpty String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(manager);
        helper.setSubject(subject);
        helper.setText(text);
        mailSender.send(mimeMessage);
    }
}
