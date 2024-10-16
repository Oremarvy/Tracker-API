package com.Oreoluwa.Tracker.API.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateActivityRequest {
    private String subject;

    private String description;

    private List<String> imageUrl;

    private String supervisor;

    private String linkedinUrl;

    private List<MultipartFile> file;


}