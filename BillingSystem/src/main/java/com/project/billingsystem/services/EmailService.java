package com.project.billingsystem.services;

import com.project.billingsystem.repositories.AppUserRepository;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.Properties;

@Service
public class EmailService {


    public EmailService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    private final AppUserRepository appUserRepository;

    //@Scheduled(cron = "0 0 10 1 * *") 1th of every month at 10 am trigger email sending
    //@Scheduled(cron = "0 * * * * *") // Every minute for testing
    public void sendEmail() {
        Dotenv dotenv = Dotenv.configure().load();
        String username = dotenv.get("EMAIL");
        String password = dotenv.get("PASS");
        System.out.println("username: " + username + "\n\n password" + password);
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("")
            );
            message.setSubject("This is a test email");
            message.setContent("Dear,User"
                    + "\n\n Hello!","");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }



    }


}
