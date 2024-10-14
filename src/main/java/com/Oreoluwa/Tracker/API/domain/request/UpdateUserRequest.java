package com.Oreoluwa.Tracker.API.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String supervisor;

    private String department;

    private String team;

    private String school;

    private String linkedinUrl;

}