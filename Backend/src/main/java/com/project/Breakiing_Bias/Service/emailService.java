package com.project.Breakiing_Bias.Service;

import com.project.Breakiing_Bias.Entity.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;
import sibInvoker.ApiClient;

import java.util.List;

@Service
public class emailService {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${app.base-url}")
    private String baseUrl;

    public void linkSender(Users user, String token) {
        String link = baseUrl + "/api/auth/verify?token=" + token;
        sendEmail(
            user.getEmail(),
            "Email Verification - Breaking Bias",
            "Click the link below to verify your email:\n\n" + link
        );
    }

    public void forgotLinkSender(Users user, String token) {
        String link = baseUrl + "/api/auth/forgot/verify?token=" + token;
        sendEmail(
            user.getEmail(),
            "Password Reset - Breaking Bias",
            "Click the link below to reset your password:\n\n" + link
        );
    }

    private void sendEmail(String toEmail, String subject, String body) {
        try {
            ApiClient client = new ApiClient();
            client.setApiKey(brevoApiKey);

            TransactionalEmailsApi api = new TransactionalEmailsApi(client);

            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setEmail("abcae3001@smtp-brevo.com");
            sender.setName("Breaking Bias");

            SendSmtpEmailTo to = new SendSmtpEmailTo();
            to.setEmail(toEmail);

            SendSmtpEmail email = new SendSmtpEmail();
            email.setSender(sender);
            email.setTo(List.of(to));
            email.setSubject(subject);
            email.setTextContent(body);

            api.sendTransacEmail(email);
            System.out.println("=== EMAIL SENT TO: " + toEmail);

        } catch (Exception e) {
            System.out.println("=== EMAIL FAILED: " + e.getMessage());
            throw new RuntimeException("Email sending failed: " + e.getMessage());
        }
    }
}
