package com.Oreoluwa.Tracker.API.controller;

import com.Oreoluwa.Tracker.API.Exception.ApiRequestException;
import com.Oreoluwa.Tracker.API.domain.request.CreateUserRequest;
import com.Oreoluwa.Tracker.API.domain.response.CreateUserResponse;
import com.Oreoluwa.Tracker.API.domain.response.UpdateUserResponse;
import com.Oreoluwa.Tracker.API.model.UserModel;
import com.Oreoluwa.Tracker.API.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/users")

public class UserController {


    @Autowired
    private UserService userService;

    //Create a new user
    @PostMapping()
    public ResponseEntity<CreateUserResponse>  createUser(@Valid @RequestBody CreateUserRequest request) {
       log.info("Request payload : {}", request);
        CreateUserResponse response= userService.createUser(request);

          //new CreateUserResponse("Active", "User profile created successfully");
        return  ResponseEntity.ok().body(response);

    }

    //Update a user
    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable long id, @RequestBody CreateUserRequest userDetails) {
        UpdateUserResponse response= userService.updateUser(id, userDetails);

        return ResponseEntity.ok().body(response);
    }


    //Get all users
    @GetMapping()
    public List<UserModel> getAllUsers(){

        return userService.getAllUsers();
    }


    //View user profile
    @GetMapping("/{id}")
    public UserModel viewUserProfile(@PathVariable long id) {
         Optional<UserModel> user = userService.viewUserProfile(id);

        if (user.isEmpty()){
            throw new ApiRequestException("User is not found ");
        }
        return user.get();
    }
}