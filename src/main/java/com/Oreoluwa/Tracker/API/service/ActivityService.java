package com.Oreoluwa.Tracker.API.service;


import com.Oreoluwa.Tracker.API.Exception.ApiRequestException;
import com.Oreoluwa.Tracker.API.domain.request.ActivityDateRangeRequest;
import com.Oreoluwa.Tracker.API.domain.request.CreateActivityRequest;
import com.Oreoluwa.Tracker.API.domain.request.UpdateActivityRequest;
import com.Oreoluwa.Tracker.API.domain.response.*;
import com.Oreoluwa.Tracker.API.model.DailyActivity;
import com.Oreoluwa.Tracker.API.model.UserModel;
import com.Oreoluwa.Tracker.API.repository.ActivityRepository;

import com.Oreoluwa.Tracker.API.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Timestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import java.util.stream.Collectors;


@Slf4j
@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public CreateActivityResponse createDailyActivity(String userIdRequest, CreateActivityRequest activity) throws ApiRequestException {

        Optional<UserModel> userId = userRepository.findById(Long.valueOf(userIdRequest));
        if (!userId.isPresent()) {
            throw new ApiRequestException("You are not a valid user on the system");
        }
        UserModel userModel = userId.get();
        LocalDate createdDate = LocalDate.now();
        LocalDateTime startDate = createdDate.atStartOfDay();
        log.info("LocalDateTime ----> {}", startDate);
        log.info("Local Date ----> {}", LocalDate.now());
        Optional<DailyActivity> existingActivity = activityRepository
                .findByUserIdAndCreatedDate(userModel, startDate);

        if (existingActivity.isPresent()) {
            throw new ApiRequestException("User has already created an activity for today.");
        }


        DailyActivity createDailyActivity = new DailyActivity();
        // save image to cloudinary here first and save the path to your setImage
        //createDailyActivity.setImageUrl(cloudinaryResponse.getUrl());
        List<String> imageUrlList;
        if (!activity.getFile().isEmpty()) {

            imageUrlList = new ArrayList<>();
            //for (activity.getFile().)
            activity.getFile().stream().forEach(files ->
            {
                CloudinaryResponse response = cloudinaryService.uploadFile(files);
                imageUrlList.add(response.getSecureUrl());

            });

            createDailyActivity.setImageUrl(String.join(",",imageUrlList) );
        }
        createDailyActivity.setUserId(userModel);
        createDailyActivity.setSubject(activity.getSubject());
        createDailyActivity.setDescription((activity.getDescription()));
        createDailyActivity.setCreatedDate(LocalDateTime.now());
        createDailyActivity.setSupervisor(activity.getSupervisor());
        createDailyActivity.setLinkedinUrl(activity.getLinkedinUrl());

        activityRepository.save(createDailyActivity);
        entityManager.flush();  // Force Hibernate to send SQL update to the database

        return CreateActivityResponse.builder().status("Completed")
                .message("Activity created successfully").build();
    }


    public UpdateActivityResponse updateDailyActivity(long id, UpdateActivityRequest details, long userId) throws ApiRequestException {

        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ApiRequestException("User not found");
        }


        Optional<DailyActivity> findActivity = activityRepository
                .findById(id);
        if (findActivity.isEmpty()) {
            throw new ApiRequestException("Update doesn't exist");
        }
        UserModel userModel = userOptional.get();
        DailyActivity updateDailyActivity = findActivity.get();
        List<String> fileUploadResponse = new ArrayList<>();


        if (details.getFile() != null && !details.getFile().isEmpty()) {
            details.getFile().stream().forEach(file -> {
                CloudinaryResponse response = cloudinaryService.uploadFile(file);
                fileUploadResponse.add(response.getSecureUrl());
            });

            updateDailyActivity.setImageUrl(String.join(",",fileUploadResponse) );
        }

        updateDailyActivity.setUserId(userModel);
        updateDailyActivity.setSubject(details.getSubject());
        updateDailyActivity.setDescription(details.getDescription());
        updateDailyActivity.setSupervisor(details.getSupervisor());
        updateDailyActivity.setLinkedinUrl(details.getLinkedinUrl());
        updateDailyActivity.setUpdatedDate(LocalDateTime.now());

        activityRepository.save(updateDailyActivity);
        return UpdateActivityResponse.builder().status("Updated")
                .message("Activity updated successfully").build();

    }

//    public String uploadImage(long id, MultipartFile file) throws Exception {
//        // 1. Fetch the activity by ID
//        Optional<DailyActivity> optionalActivity = activityRepository.findById(id);
//
//        if (optionalActivity.isEmpty()) {
//            throw new ApiRequestException("Activity not found with ID: " + id);
//        }
//        if (file.isEmpty()) {
//            throw new ApiRequestException("File is empty. Please upload a valid image.");
//        }
//
//        DailyActivity activity = optionalActivity.get();
//
//        // 2. Upload the image to Cloudinary
//        CloudinaryResponse response;
//        try {
//            response = cloudinaryService.uploadFile(file);
//        } catch (ApiRequestException e) {
//            throw new ApiRequestException("Image upload failed");
//        }
//
//        // 3. Update the activity with the Cloudinary URL
//        activity.setImageUrl(response.getSecureUrl());
//
//
//
//        // 4. Save the updated activity
//        activityRepository.save(activity);
//
//        return activity.getImageUrl();
//    }
//

    public ViewOneActivityResponse viewOneActivity(long id) {
        Optional<DailyActivity> optionalDailyActivity = activityRepository.findById(id);
        if(optionalDailyActivity.isEmpty()) throw new ApiRequestException("Activity not found with id \" + id");

        DailyActivity foundActivity = optionalDailyActivity.get();
        Optional<UserModel> userModel = userRepository.findById(foundActivity.getUserId().getId());
        if(userModel.isEmpty()) throw new ApiRequestException("User not found with id ");

        UserModel user = userModel.get();

        activityRepository.save(foundActivity);
        return ViewOneActivityResponse.builder()
                .supervisor(foundActivity.getSupervisor())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .description(foundActivity.getDescription())
                .subject(foundActivity.getSubject())
                .linkedInUrl(foundActivity.getLinkedinUrl())
                .imageUrl(Arrays.asList(foundActivity.getImageUrl().split(",")))
                .build();
    }

    public List<DailyActivity> getAllActivities() {

        return activityRepository.findAll();
    }

    public List<ActivityDateRangeResponse> getActivityByDateRange(ActivityDateRangeRequest dateRangeRequest) throws ApiRequestException {

        String startDateStr = dateRangeRequest.getStartDate();
        String endDateStr = dateRangeRequest.getEndDate();

        // Parse strings to LocalDate
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        // Convert LocalDate to LocalDateTime for the start and end of the day
        LocalDateTime startDateTime = startDate.atStartOfDay(); // 00:00:00
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX); // 23:59:59


        List<DailyActivity> activities =
                activityRepository.findAllByCreatedDateBetween
                        (startDateTime,
                                endDateTime);
        if (activities.isEmpty()) {
            throw new ApiRequestException("No activities found for the specified date range");
        }

        //response.setImage();
        return activities.stream().map(mappedActivities -> {
            ActivityDateRangeResponse response = new ActivityDateRangeResponse();
            response.setSubject(mappedActivities.getSubject());
            response.setSupervisor(mappedActivities.getSupervisor());
            response.setDescription(mappedActivities.getDescription());
            response.setImageUrl(Collections.singletonList(mappedActivities.getImageUrl()));
            response.setCreatedDate(mappedActivities.getCreatedDate());
            response.setUpdatedDate(mappedActivities.getUpdatedDate());
            response.setLinkedinUrl(mappedActivities.getLinkedinUrl());
            return response;
        }).collect(Collectors.toList());


    }


}