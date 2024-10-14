package com.Oreoluwa.Tracker.API.service;

import com.Oreoluwa.Tracker.API.Exception.ApiRequestException;
import com.Oreoluwa.Tracker.API.domain.request.CreateUserRequest;
import com.Oreoluwa.Tracker.API.domain.response.CreateUserResponse;
import com.Oreoluwa.Tracker.API.domain.response.UpdateUserResponse;
import com.Oreoluwa.Tracker.API.model.UserModel;
import com.Oreoluwa.Tracker.API.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public CreateUserResponse createUser(CreateUserRequest user) {
        Optional<UserModel> userModel= userRepository.findByEmail(user.getEmail());
        if(userModel.isPresent()){
            throw new ApiRequestException("User with this email already exists");
        }
       Optional<UserModel> findUserByPhoneNumber = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if(findUserByPhoneNumber.isPresent()){
            throw new ApiRequestException("PhoneNumber already exists");
        }
        UserModel createUser = new UserModel();
        createUser.setFirstName(user.getFirstName());
        createUser.setLastName(user.getLastName());
        createUser.setEmail(user.getEmail());
        createUser.setPhoneNumber(user.getPhoneNumber());
        createUser.setAddress(user.getAddress());
        createUser.setSupervisor(user.getSupervisor());
        createUser.setDepartment(user.getDepartment());
        createUser.setTeam(user.getTeam());
        createUser.setSchool(user.getSchool());
        createUser.setLinkedinUrl(user.getLinkedinUrl());


        userRepository.save(createUser);

        return  CreateUserResponse.builder().status("Active").message("User is created successfully ").build();
    }

    public UpdateUserResponse updateUser(long id, CreateUserRequest userDetails) {
        Optional<UserModel> findUser= userRepository.findById(id);
        if(findUser.isEmpty()){
            throw  new ApiRequestException("Update doesn't exist");
        }

        UserModel getUser= findUser.get();
        getUser.setFirstName(userDetails.getFirstName());
        getUser.setLastName(userDetails.getLastName());
        getUser.setEmail(userDetails.getEmail());
        getUser.setPhoneNumber(userDetails.getPhoneNumber());
        getUser.setAddress(userDetails.getAddress());
        getUser.setSupervisor(userDetails.getSupervisor());
        getUser.setDepartment(userDetails.getDepartment());
        getUser.setTeam(userDetails.getTeam());
        getUser.setSchool(userDetails.getSchool());
        getUser.setLinkedinUrl(userDetails.getLinkedinUrl());

        userRepository.save(getUser);
        return UpdateUserResponse.builder().status("Completed").message("User updated successfully").build();
    }

    public Optional<UserModel> viewUserProfile(long id){
        return userRepository.findById(id);
    }

    public List<UserModel> getAllUsers(){
        return userRepository.findAll();
    }




}