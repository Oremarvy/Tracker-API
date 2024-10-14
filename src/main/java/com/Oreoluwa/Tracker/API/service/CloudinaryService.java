package com.Oreoluwa.Tracker.API.service;


import com.Oreoluwa.Tracker.API.Exception.ApiRequestException;
import com.Oreoluwa.Tracker.API.domain.response.CloudinaryResponse;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;


    public CloudinaryResponse uploadFile(MultipartFile file) throws IOException {
        System.out.println("Uploading file to Cloudinary...");
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            System.out.println("Upload result: " + uploadResult);
            return new CloudinaryResponse(
                    (String) uploadResult.get("public_id"),
                    (String) uploadResult.get("secure_url"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Cloudinary upload failed: " + e.getMessage());
        }

    }
}