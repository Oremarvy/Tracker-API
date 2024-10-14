package com.Oreoluwa.Tracker.API.service;


import com.Oreoluwa.Tracker.API.Exception.ApiRequestException;
import com.Oreoluwa.Tracker.API.domain.request.ActivityDateRangeRequest;
import com.Oreoluwa.Tracker.API.domain.request.CreateActivityRequest;
import com.Oreoluwa.Tracker.API.domain.request.UpdateActivityRequest;
import com.Oreoluwa.Tracker.API.domain.response.ActivityDateRangeResponse;
import com.Oreoluwa.Tracker.API.domain.response.CloudinaryResponse;
import com.Oreoluwa.Tracker.API.domain.response.CreateActivityResponse;
import com.Oreoluwa.Tracker.API.domain.response.UpdateActivityResponse;
import com.Oreoluwa.Tracker.API.model.DailyActivity;
import com.Oreoluwa.Tracker.API.repository.ActivityRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;



@Slf4j
@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CloudinaryService cloudinaryService;


    public CreateActivityResponse createDailyActivity(CreateActivityRequest activity, MultipartFile file) throws IOException {
        Optional<DailyActivity> dailyActivity = activityRepository
                .findBySubject(activity.getSubject());

        if (dailyActivity.isPresent()) {
            throw new ApiRequestException("Activity already exists");
        }




        DailyActivity createDailyActivity = new DailyActivity();
// save image to cloudinary here first and save the path to your setImage
        //createDailyActivity.setImageUrl(cloudinaryResponse.getUrl());
        if (!file.isEmpty()) {
            CloudinaryResponse response = cloudinaryService.uploadFile(file);
            createDailyActivity.setImageUrl(response.getSecureUrl());
        }
        createDailyActivity.setSubject(activity.getSubject());
        createDailyActivity.setDescription((activity.getDescription()));
        //createDailyActivity.setImage(activity.getImage());
        createDailyActivity.setSupervisor(activity.getSupervisor());
        createDailyActivity.setLinkedinUrl(activity.getLinkedinUrl());

        activityRepository.save(createDailyActivity);

        return CreateActivityResponse.builder().status("Completed")
                .message("Activity created successfully").build();
    }


    public UpdateActivityResponse updateDailyActivity(long id, UpdateActivityRequest details, MultipartFile file) throws IOException {
        Optional<DailyActivity> findActivity = activityRepository
                .findBySubject(details.getSubject());

        if (findActivity.isEmpty()) {
            throw new ApiRequestException("Update doesn't exist");
        }


        DailyActivity updateDailyActivity = findActivity.get();

        if (!file.isEmpty()) {
            CloudinaryResponse response = cloudinaryService.uploadFile(file);
            updateDailyActivity.setImageUrl(response.getSecureUrl());
        }


        updateDailyActivity.setSubject(details.getSubject());
        updateDailyActivity.setDescription(details.getDescription());
        updateDailyActivity.setSupervisor(details.getSupervisor());
        updateDailyActivity.setLinkedinUrl(details.getLinkedinUrl());

        activityRepository.save(updateDailyActivity);
        return UpdateActivityResponse.builder().status("Updated")
                .message("Activity updated successfully").build();

    }


    public String uploadImage(long id, MultipartFile file) throws Exception {
        // 1. Fetch the activity by ID
        Optional<DailyActivity> optionalActivity = activityRepository.findById(id);

        if (optionalActivity.isEmpty()) {
            throw new ApiRequestException("Activity not found with ID: " + id);
        }
        if (file.isEmpty()) {
            throw new ApiRequestException("File is empty. Please upload a valid image.");
        }

        DailyActivity activity = optionalActivity.get();

        // 2. Upload the image to Cloudinary
        CloudinaryResponse response;
        try {
            response = cloudinaryService.uploadFile(file);
        } catch (IOException e) {
            throw new ApiRequestException("Image upload failed");
        }

        // 3. Update the activity with the Cloudinary URL
        activity.setImageUrl(response.getSecureUrl());



        // 4. Save the updated activity
        activityRepository.save(activity);

        return activity.getImageUrl();
    }


    public Optional<DailyActivity> viewOneActivity(long id) {

        return activityRepository.findById(id);
    }

    public List<DailyActivity> getAllActivities() {

        return activityRepository.findAll();
    }

    public List<ActivityDateRangeResponse> getActivityByDateRange(ActivityDateRangeRequest dateRangeRequest) throws ParseException {

        //SimpleDateFormat endDate= new SimpleDateFormat("yyyy-MM-dd");
       String startDateStr = dateRangeRequest.getStartDate() + " 00:00:00";
       String endDateStr = dateRangeRequest.getEndDate() + " 23:59:59";

        Timestamp startDate = Timestamp.valueOf(startDateStr);
        Timestamp endDate = Timestamp.valueOf(endDateStr);

//        startDate.applyLocalizedPattern(dateRangeRequest.getDateRange1());
//        endDate.applyLocalizedPattern(dateRangeRequest.getDateRange2());
        List<DailyActivity> activities =
                activityRepository.findAllByCreatedDateBetween
                        (startDate,
                                endDate);
        if (activities.isEmpty()) {
            throw new ApiRequestException("No activities found for the specified date range");
        }

        //response.setImage();
        return activities.stream().map(mappedActivities -> {
                    ActivityDateRangeResponse response = new ActivityDateRangeResponse();
                    response.setSubject(mappedActivities.getSubject());
                    response.setSupervisor(mappedActivities.getSupervisor());
                    response.setDescription(mappedActivities.getDescription());
                    //response.setImage();
                    response.setCreatedDate(mappedActivities.getCreatedDate());
                    response.setUpdatedDate(mappedActivities.getUpdatedDate());
                    response.setLinkedinUrl(mappedActivities.getLinkedinUrl());
                    return response;
                }).collect(Collectors.toList());





    }



}