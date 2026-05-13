package com.project.Breakiing_Bias.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.UuidGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

     @Column(unique = true)
    private String username;

     private String password;

    @NotBlank(message = "Email is Required")
    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private boolean isVerified = false;

    private  String verificationToken;
}
