package com.Oreoluwa.Tracker.API.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;


    // @Size(min = 20, max = 50, message = "First name must be between 20 and 50 characters")
    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "password is very essential")
    private String password;

    private String email;

    private String phoneNumber;


    private String address;

    @Column(nullable = false)
    @NotBlank(message = "supervisor is mandatory")
    private String supervisor;


    private String department;


    private String team;


    private String school;


    private String linkedinUrl;

    private String profilePictureUrl;


    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;


    @PrePersist
    private void onCreate() {

        createdDate = new Date();
    }

    @PreUpdate
    private void onUpdate() {

        updatedDate = new Date();
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return username;
    }
}