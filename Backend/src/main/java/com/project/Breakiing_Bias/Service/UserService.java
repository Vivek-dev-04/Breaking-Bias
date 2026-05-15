package com.project.Breakiing_Bias.Service;

import com.project.Breakiing_Bias.Entity.Users;
import com.project.Breakiing_Bias.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    @Autowired
    private emailService emailService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Users findUser(String username){
        return repo.findByUsername(username);
    }
    public void startRegistration(String email){

        Users existingUser = repo.findByEmail(email);

        // user already exists
        if(existingUser != null){

            // if already verified
            if(existingUser.isVerified()){
                throw new RuntimeException("Email already exists");
            }

            // resend verification token
            String token = UUID.randomUUID().toString();

            existingUser.setVerificationToken(token);

            repo.save(existingUser);

            emailService.linkSender(existingUser, token);

            return;
        }

        // new user
        Users user = new Users();

        user.setEmail(email);
        user.setVerified(false);

        String token = UUID.randomUUID().toString();

        user.setVerificationToken(token);

        repo.save(user);

        emailService.linkSender(user, token);
    }
    public List<Users> getAllUsers(){
        return repo.findAll();
    }

    public boolean verifyEmail(String token){
        Users user = repo.findByVerificationToken(token);

        if(user == null){
            throw new RuntimeException("User is not verified");
        }

        user.setVerified(true);
        repo.save(user);
        return user.isVerified();
    }

    public Users completeRegistration(String email,String username,String password){
        Users user = repo.findByEmail(email);

        if(!user.isVerified()){
            throw new RuntimeException("Email.is not verified");
        }

        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        user.setVerificationToken(null);
        return repo.save(user);
    }
    public Users check(Users user){
        Users check = repo.findByUsername(user.getUsername());

        if(check == null) return null;

        if(passwordEncoder.matches(user.getPassword(), check.getPassword())){
                return check;
        }
        return null;
    }


    public void startForgotPassword(String username) {
        // find user by username → get their email from DB
        Users user = repo.findByUsername(username);
        if (user == null) throw new RuntimeException("Username not found");

        // reuse existing startRegistration with their email
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);

        repo.save(user);

        emailService.forgotLinkSender(user,token);
    }

    public void completeForgotPassword(String token, String newPassword) {
        // find user by token
        Users user = repo.findByVerificationToken(token);
        if (user == null) throw new RuntimeException("Invalid token");

        // update password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setVerificationToken(null); // clear token
         repo.save(user);
    }
}
