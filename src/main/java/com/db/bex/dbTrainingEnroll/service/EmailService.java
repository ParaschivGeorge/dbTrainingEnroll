package com.db.bex.dbTrainingEnroll.service;

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

    JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(@Size(min = 1) List<String> recipients, @NotEmpty String text, @NotEmpty String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(recipients.get(0));
        helper.setSubject(subject);
        if(recipients.size() > 1)
        {
            recipients.remove(0);
            helper.setBcc(recipients.toArray(new String[recipients.size()]));
        }
        helper.setText(text);
        mailSender.send(mimeMessage);
    }
}
