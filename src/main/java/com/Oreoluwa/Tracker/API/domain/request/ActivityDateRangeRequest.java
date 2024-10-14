package com.Oreoluwa.Tracker.API.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDateRangeRequest {

    private String startDate;

    private String endDate;
}