package com.project.Breakiing_Bias.Service;

import com.project.Breakiing_Bias.Entity.Users;
import com.project.Breakiing_Bias.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class emailService {

    @Value("${app.base-url}")
    private String baseUrl;

    @Autowired
    private UserRepo repo;

    @Autowired
    private JavaMailSender mailSender;


    public void linkSender(Users user, String token){

        String link = baseUrl+"/api/auth/verify?token="+token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javathhompson92@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Email Verification");
        message.setText("Click the link given below to verify your mail id:");
        message.setText(link);

        mailSender.send(message);
    }
    public void forgotLinkSender(Users user, String token){

        String link = baseUrl+"/api/auth/forgot/verify?token="+token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javathhompson92@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Password Reset");
        message.setText("Click the link given below to reset your password:\n\n"+link);

        mailSender.send(message);
    }

}
