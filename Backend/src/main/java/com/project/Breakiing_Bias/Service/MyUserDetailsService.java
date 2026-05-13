package com.project.Breakiing_Bias.Service;

import com.project.Breakiing_Bias.Entity.Users;
import com.project.Breakiing_Bias.Repository.UserPrincipal;
import com.project.Breakiing_Bias.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Looking up username: " + username); // ← add this

        Users user = repo.findByUsername(username);

        if (user == null) {
            System.out.println("User NOT found!"); // ← add this
            throw new UsernameNotFoundException("User Not Found");
        }

        System.out.println("Found user: " + user.getUsername()); // ← add this
        System.out.println("Password hash: " + user.getPassword()); // ← add this

        return new UserPrincipal(user);
    }
}
