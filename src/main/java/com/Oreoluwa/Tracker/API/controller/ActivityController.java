package com.Oreoluwa.Tracker.API.controller;



import com.Oreoluwa.Tracker.API.Exception.ApiRequestException;
import com.Oreoluwa.Tracker.API.domain.request.ActivityDateRangeRequest;
import com.Oreoluwa.Tracker.API.domain.request.CreateActivityRequest;
import com.Oreoluwa.Tracker.API.domain.request.UpdateActivityRequest;
import com.Oreoluwa.Tracker.API.domain.response.ActivityDateRangeResponse;
import com.Oreoluwa.Tracker.API.domain.response.CreateActivityResponse;
import com.Oreoluwa.Tracker.API.domain.response.UpdateActivityResponse;
import com.Oreoluwa.Tracker.API.domain.response.ViewOneActivityResponse;
import com.Oreoluwa.Tracker.API.model.DailyActivity;
import com.Oreoluwa.Tracker.API.service.ActivityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.text.ParseException;

import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/activity")
public class ActivityController {


    @Autowired
    private ActivityService activityService;

    //Create Daily Activity
    @PostMapping()
    public ResponseEntity<CreateActivityResponse> createDailyActivity(@RequestHeader("userId") String userId, @Valid @ModelAttribute CreateActivityRequest activity) throws ApiRequestException {
        log.info("Request Payload: {} ", activity);
        log.info("user id from header {}", userId);
        CreateActivityResponse response= activityService.createDailyActivity(userId,activity);
         //new ("Completed", "Well-done");
        return ResponseEntity.ok().body(response);
    }

    //Update Daily activity
    @PutMapping("/{id}")
    public ResponseEntity<UpdateActivityResponse> updateDailyActivity(@RequestHeader("user_id") long userId, @PathVariable long id, @Valid @ModelAttribute UpdateActivityRequest details) throws ApiRequestException {
        UpdateActivityResponse response = activityService.updateDailyActivity(id, details, userId);

        return ResponseEntity.ok().body(response);
                //new ("Completed", "Updated Successfully");
    }

    //View all activities
    @GetMapping("/all-activity")
    public List<DailyActivity> getAllActivities(){

        return activityService.getAllActivities();
    }

    //View One Activity
    @GetMapping("/{id}")
    public ResponseEntity<ViewOneActivityResponse> viewOneActivity(@PathVariable long id) {
        ViewOneActivityResponse activity = activityService.viewOneActivity(id);
        return ResponseEntity.ok(activity);
    }

    @PostMapping("/activity-by-date-range")
    public ResponseEntity<List<ActivityDateRangeResponse>> activityByDateRange(@RequestBody ActivityDateRangeRequest request) throws ApiRequestException {
        List<ActivityDateRangeResponse> activityDateRangeResponses= activityService.getActivityByDateRange(request);
        return ResponseEntity.ok().body(activityDateRangeResponses);
    }

//    @PostMapping("/upload-image/{id}")
//    public ResponseEntity<String> uploadImage(@PathVariable("id") long id,
//                                              @RequestParam("file") MultipartFile file) {
//        try {
//            String imageUrl = activityService.uploadImage(id, file);
//            return ResponseEntity.ok(imageUrl);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
//        }
//    }









}