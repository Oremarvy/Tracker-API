package com.Oreoluwa.Tracker.API.controller;



import com.Oreoluwa.Tracker.API.Exception.ApiRequestException;
import com.Oreoluwa.Tracker.API.domain.request.ActivityDateRangeRequest;
import com.Oreoluwa.Tracker.API.domain.request.CreateActivityRequest;
import com.Oreoluwa.Tracker.API.domain.request.UpdateActivityRequest;
import com.Oreoluwa.Tracker.API.domain.response.ActivityDateRangeResponse;
import com.Oreoluwa.Tracker.API.domain.response.CreateActivityResponse;
import com.Oreoluwa.Tracker.API.domain.response.UpdateActivityResponse;
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
    public ResponseEntity<CreateActivityResponse> createDailyActivity(@Valid @RequestBody CreateActivityRequest activity, MultipartFile file) throws IOException {
        log.info("Request Payload: {} ", activity);
        CreateActivityResponse response= activityService.createDailyActivity(activity, file);
         //new ("Completed", "Well-done");
        return ResponseEntity.ok().body(response);
    }

    //Update Daily activity
    @PutMapping("/{id}")
    public ResponseEntity<UpdateActivityResponse> updateDailyActivity(@PathVariable long id, @RequestBody UpdateActivityRequest details, MultipartFile file) throws IOException {
        UpdateActivityResponse response = activityService.updateDailyActivity(id, details, file);

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
    public DailyActivity viewOneActivity(@PathVariable long id) {
        Optional <DailyActivity> activity = activityService.viewOneActivity(id);
        if (activity.isEmpty()){
            throw new ApiRequestException("Activity not found");
        }
        return activity.get();
    }
    @GetMapping("/activity-by-date-range")
    public ResponseEntity<List<ActivityDateRangeResponse>> activityByDateRange(@RequestBody ActivityDateRangeRequest request) throws ParseException {
        List<ActivityDateRangeResponse> activityDateRangeResponses= activityService.getActivityByDateRange(request);
        return ResponseEntity.ok().body(activityDateRangeResponses);
    }

    @PostMapping("/upload-image/{id}")
    public ResponseEntity<String> uploadImage(@PathVariable("id") long id,
                                              @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = activityService.uploadImage(id, file);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
        }
    }









}