package com.project.Breakiing_Bias.Repository;

import com.project.Breakiing_Bias.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<Users, UUID> {
    public Users findByUsername(String userName);
    public Users findByVerificationToken(String token);
    public Users findByEmail(String email);
}
