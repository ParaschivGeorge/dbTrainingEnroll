package com.db.bex.dbTrainingEnroll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

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
//            helper.setTo(receivers.get(0));
            helper.setTo("stefaneva25@yahoo.com");
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
//        helper.setTo(manager);
        helper.setTo("stefaneva25@yahoo.com");
        helper.setSubject(subject);
        helper.setText(text);
        mailSender.send(mimeMessage);
    }
}
