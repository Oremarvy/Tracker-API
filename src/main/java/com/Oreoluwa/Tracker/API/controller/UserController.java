package com.Oreoluwa.Tracker.API.controller;

import com.Oreoluwa.Tracker.API.Auth.AuthenticationResponse;
import com.Oreoluwa.Tracker.API.Auth.AuthenticationService;
import com.Oreoluwa.Tracker.API.Exception.ApiRequestException;
import com.Oreoluwa.Tracker.API.domain.request.CreateUserRequest;
import com.Oreoluwa.Tracker.API.domain.request.UpdateUserRequest;
import com.Oreoluwa.Tracker.API.model.UserModel;
import com.Oreoluwa.Tracker.API.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/users")

public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    //Create a new user
    @PostMapping()
    public ResponseEntity<AuthenticationResponse>  createUser(@Valid @ModelAttribute CreateUserRequest request) {
       log.info("Request payload : {}", request);
        AuthenticationResponse response= userService.createUser(request);

          //new CreateUserResponse("Active", "User profile created successfully");
        return  ResponseEntity.ok(response);

    }

    //Update a user
    @PutMapping("/{id}")
    public ResponseEntity<AuthenticationResponse> updateUser(@PathVariable long id, @Valid @ModelAttribute UpdateUserRequest userDetails) {
        AuthenticationResponse response= userService.updateUser(id, userDetails);

        return ResponseEntity.ok(response);
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

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }
}