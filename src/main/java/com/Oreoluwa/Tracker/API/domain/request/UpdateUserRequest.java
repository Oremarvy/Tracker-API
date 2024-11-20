package com.Oreoluwa.Tracker.API.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String address;

    private String supervisor;

    private String department;

    private String team;

    private String school;

    private String linkedinUrl;

    private MultipartFile file;

}