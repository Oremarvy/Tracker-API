package com.Oreoluwa.Tracker.API.domain.request;

import com.Oreoluwa.Tracker.API.Exception.ApiRequestException;
import com.Oreoluwa.Tracker.API.model.UserModel;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateActivityRequest {

    @Column(name="subject", nullable = false)
    @NotBlank(message = "subject is mandatory")
    private String subject;

    @Column(name="description", nullable = false)
    @NotBlank(message = "description is mandatory")
    private String description;

    @Column(name="supervisor", nullable = false)
    @NotBlank(message = "supervisor is mandatory")
    private String supervisor;

    @Column(name="linkedinUrl", nullable = false)
    @NotBlank(message = "linkedinUrl is mandatory")
    private String linkedinUrl;

    private List<MultipartFile> file;

    @Column(nullable = false)
    private UserModel userId;



}