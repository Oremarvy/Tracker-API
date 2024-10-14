package com.Oreoluwa.Tracker.API.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.PixelGrabber;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateActivityResponse {

    private String status;
    private String message;




}