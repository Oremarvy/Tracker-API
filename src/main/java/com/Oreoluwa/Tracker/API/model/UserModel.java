package com.Oreoluwa.Tracker.API.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;


    // @Size(min = 20, max = 50, message = "First name must be between 20 and 50 characters")
    private String firstName;

    private String lastName;

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
}