package com.Oreoluwa.Tracker.API.domain.response;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityDateRangeResponse {
    private String subject;
    private String description;
    private String imageUrl;
    private String supervisor;
    private String linkedinUrl;
    private Date createdDate;
    private Date updatedDate;
}