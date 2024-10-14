package com.Oreoluwa.Tracker.API.model;


import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;
import jakarta.persistence.*;

import lombok.Data;



import java.util.Date;

@Entity
@Table(name= "activity")
@Data
public class DailyActivity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private long id;


        private String subject;


        private String description;

        private String imageUrl;


        private String supervisor;


        private String linkedinUrl;

        @Temporal(TemporalType.TIMESTAMP)
        private Date createdDate;

        @Temporal(TemporalType.TIMESTAMP)
        private Date updatedDate;

        @PrePersist
        private void onCreate(){
                createdDate= new Date();
        }
        @PreUpdate
        private void onUpdate(){
                updatedDate= new Date();
        }

}