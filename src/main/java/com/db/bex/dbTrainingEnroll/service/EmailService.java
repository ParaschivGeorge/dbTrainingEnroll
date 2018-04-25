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

    @Value("spring.mail.host")
    private String host;

    @Value("spring.mail.port")
    private String port;

    @Value("spring.mail.username")
    private String username;

    @Value("spring.mail.password")
    private String password;

    @Value("spring.mail.propertiesmail.smtp.auth")
    private String smtpAuth;

    @Value("spring.mail.propertiesmail.smtp.starttls.enable")
    private String starttlsEnable;

    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailToUsers(List<String> receivers, @NotEmpty String text, @NotEmpty String subject) throws MessagingException {

        try {
            String location = "Torvalds";

            Properties props = System.getProperties();

            props.put("spring.mail.host",host);
            props.put("spring.mail.port",port);
            props.put("spring.mail.username", username);
            props.put("spring.mail.password", password);
            props.put("spring.mail.properties.mail.smtp.auth",smtpAuth);
            props.put("spring.mail.properties.mail.smtp.starttls.enable" , starttlsEnable);

            Session session = Session.getInstance(props, null);


            MimeMessage message = new MimeMessage(session);
            message.addHeaderLine("method=REQUEST");
            message.addHeaderLine("charset=UTF-8");
            message.addHeaderLine("component=vevent");
            message.setFrom(new InternetAddress(username));
            message.setSubject(subject);

            String[] bccList = new String[receivers.size()];
            bccList = receivers.toArray(bccList);

            for (String address : bccList) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
            }

            StringBuffer sb = new StringBuffer();

            StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                    "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
                    "VERSION:2.0\n" +
                    "METHOD:REQUEST\n" +
                    "BEGIN:VEVENT\n" +
                    "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + receivers + "\n" +
                    "ORGANIZER:MAILTO:xx@xx.com\n" +
                    "DTSTART:" + new Date() + "\n" +
                    "DTEND:"+ new Date() + "\n" +
                    "LOCATION:" + location + "\n" +
                    "TRANSP:OPAQUE\n" +
                    "SEQUENCE:0\n" +
                    "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n" +
                    " 000004377FE5C37984842BF9440448399EB02\n" +
                    "DTSTAMP:20051206T120102Z\n" +
                    "CATEGORIES:Meeting\n" +
                    "DESCRIPTION:This the description of the meeting.\n\n" +
                    "SUMMARY:Test meeting request\n" +
                    "PRIORITY:5\n" +
                    "CLASS:PUBLIC\n" +
                    "BEGIN:VALARM\n" +
                    "TRIGGER:PT1440M\n" +
                    "ACTION:DISPLAY\n" +
                    "DESCRIPTION:Reminder\n" +
                    "END:VALARM\n" +
                    "END:VEVENT\n" +
                    "END:VCALENDAR");


            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(text);

            messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            messageBodyPart.setHeader("Content-ID", "calendar_message");
            messageBodyPart.setDataHandler(new DataHandler(
                    new ByteArrayDataSource(buffer.toString(), "text/calendar")));// very important

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            mailSender.send(message);
            System.out.println("Done");
        } catch (Exception ex) {
            ex.printStackTrace();
        }


//       MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        for(String s : receivers)
//            System.out.println(s);
//        if(receivers.size() > 0) {
//            helper.setTo(receivers.get(0));
//            helper.setSubject(subject);
//            if (receivers.size() > 1) {
//                receivers.remove(0);
//                helper.setBcc(receivers.toArray(new String[receivers.size()]));
//            }
//            helper.setText(text);
//            mailSender.send(mimeMessage);
//        }
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
