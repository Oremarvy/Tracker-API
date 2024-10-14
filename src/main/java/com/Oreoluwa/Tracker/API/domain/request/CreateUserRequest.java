package com.Oreoluwa.Tracker.API.domain.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @Column(name = "name = first_name", nullable = false)
    @NotBlank(message = "firstName is mandatory")
    private String firstName;

    @Column(name = "name = last_name", nullable = false)
    @NotBlank(message = "lastName is mandatory")
    @Size(min = 2, max = 20, message = "Last name must be between 20 and 50 characters")
    private String lastName;

    @Column (nullable = false)
    @NotBlank(message = "Email is mandatory")
    //@Size(min = 20, max = 70, message = "Email must be between 20 and 70 characters")
    @Email(message = "Invalid email")
    private String email;


    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @NotBlank(message = "supervisor is mandatory")
    private String supervisor;

    @Column(nullable = false)
    @NotBlank(message = "department is mandatory")
    private String department;

    @Column(nullable = false)
    private String team;

    @Column(nullable = false)
    @NotBlank(message = "school is mandatory")
    private String school;

    @Column(nullable = false)
    @NotBlank(message = "linkedinUrl is mandatory")
    private String linkedinUrl;


}