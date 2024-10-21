package com.Oreoluwa.Tracker.API.repository;


import com.Oreoluwa.Tracker.API.model.DailyActivity;
import com.Oreoluwa.Tracker.API.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<DailyActivity, Long> {


    Optional<DailyActivity> findUserByIdAndCreatedDate(UserModel userId, Date createdDate);
    Optional<DailyActivity> findByUserIdAndCreatedDate(UserModel userId, LocalDateTime createdDate);
    //List<DailyActivity> findAllByUserId(String userId);


    List<DailyActivity> findAllByCreatedDateBetween (Date dateRange1, Date dateRange2);

    Optional<DailyActivity> findBySubject(String subject);
}