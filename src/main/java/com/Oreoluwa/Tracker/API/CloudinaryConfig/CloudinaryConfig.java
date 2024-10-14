package com.Oreoluwa.Tracker.API.CloudinaryConfig;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Map;

@Data



@Configuration
public class CloudinaryConfig {

    @Value("@dtqhpef61")
    private String cloudName;

    @Value("985151958185778")
    private String apiKey;

    @Value("3aDtaFr9_9SAACYlzSSAm_ATrgs")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        System.out.println("CloudinaryConfig is loaded!"); // For debug purposes
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dtqhpef61",
                "api_key", "183875518518338",
                "api_secret", "1Je5fX8WnivN4Kl7PliOhvxcyuU"
        ));

    }

}