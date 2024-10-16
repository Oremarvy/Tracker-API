package com.Oreoluwa.Tracker.API.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewOneActivityResponse {
    private long id;
    private String fullName;
    private String subject;
    private String description;
    private String supervisor;
    private String linkedInUrl;
    private List<String> imageUrl;

}