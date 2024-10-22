package com.Oreoluwa.Tracker.API.domain.response;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityDateRangeResponse {
    private String subject;
    private String description;
    private List<String> imageUrl;
    private String supervisor;
    private String linkedinUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}