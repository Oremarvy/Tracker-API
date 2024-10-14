package com.Oreoluwa.Tracker.API.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateActivityRequest {
    private String subject;

    private String description;

    private String imageUrl;

    private String supervisor;

    private String linkedinUrl;


}