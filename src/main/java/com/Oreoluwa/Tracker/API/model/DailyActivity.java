package com.Oreoluwa.Tracker.API.model;


import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.persistence.*;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.util.Date;

@Entity
@Table(name = "activity")
@Data
public class DailyActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserModel userId;

    private String subject;


    private String description;

    //@ElementCollection

    private String imageUrl;


    private String supervisor;


    private String linkedinUrl;
    private String twitterUrl;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

//    @PrePersist
//    private void onCreate() {
//        createdDate = new Date();
//    }
//
//    @PreUpdate
//    private void onUpdate() {
//        updatedDate = new Date();
//    }

}