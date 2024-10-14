package com.Oreoluwa.Tracker.API.repository;


import com.Oreoluwa.Tracker.API.model.DailyActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<DailyActivity, Long> {

    /**
     *
     * @param subject
     * @return
     */
    Optional<DailyActivity> findBySubject(String subject);


    List<DailyActivity> findAllByCreatedDateBetween (Date dateRange1, Date dateRange2);
}