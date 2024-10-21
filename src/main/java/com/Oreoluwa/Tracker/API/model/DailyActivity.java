package com.Oreoluwa.Tracker.API.model;


import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


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