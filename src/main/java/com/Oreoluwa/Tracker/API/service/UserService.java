package com.Oreoluwa.Tracker.API.service;

import com.Oreoluwa.Tracker.API.Auth.AuthenticationResponse;
import com.Oreoluwa.Tracker.API.Exception.ApiRequestException;
import com.Oreoluwa.Tracker.API.domain.request.CreateUserRequest;
import com.Oreoluwa.Tracker.API.domain.request.UpdateUserRequest;
import com.Oreoluwa.Tracker.API.domain.response.CloudinaryResponse;
import com.Oreoluwa.Tracker.API.model.Role;
import com.Oreoluwa.Tracker.API.model.UserModel;
import com.Oreoluwa.Tracker.API.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTService jwtService;


    public AuthenticationResponse createUser(CreateUserRequest user) {
        Optional<UserModel> userModel= userRepository.findByEmail(user.getEmail());
        if(userModel.isPresent()){
            throw new ApiRequestException("User with this email already exists");
        }
       Optional<UserModel> findUserByPhoneNumber = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if(findUserByPhoneNumber.isPresent()){
            throw new ApiRequestException("PhoneNumber already exists");
        }
        UserModel createUser = new UserModel();

        if (user.getFile().isEmpty()){
            CloudinaryResponse response = cloudinaryService.uploadFile(user.getFile());
            createUser.setProfilePictureUrl(response.getSecureUrl());
        }
        createUser.setFirstName(user.getFirstName());
        createUser.setLastName(user.getLastName());
        createUser.setUsername(user.getUsername());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setEmail(user.getEmail());
        createUser.setPhoneNumber(user.getPhoneNumber());
        createUser.setAddress(user.getAddress());
        createUser.setSupervisor(user.getSupervisor());
        createUser.setDepartment(user.getDepartment());
        createUser.setTeam(user.getTeam());
        createUser.setSchool(user.getSchool());
        createUser.setLinkedinUrl(user.getLinkedinUrl());
        createUser.setRole(Role.USER);
        var jwtToken = jwtService.generateToken(createUser);



        userRepository.save(createUser);

        return  AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse updateUser(long id, UpdateUserRequest userDetails) {
        Optional<UserModel> findUser= userRepository.findById(id);
        if(findUser.isEmpty()){
            throw  new ApiRequestException("Update doesn't exist");
        }

        UserModel getUser= findUser.get();
        if(userDetails.getFile() != null && !userDetails.getFile().isEmpty()){
            CloudinaryResponse response = cloudinaryService.uploadFile(userDetails.getFile());
            getUser.setProfilePictureUrl(response.getSecureUrl());
        }
        getUser.setFirstName(userDetails.getFirstName());
        getUser.setLastName(userDetails.getLastName());
        getUser.setUsername(userDetails.getUsername());
        getUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        getUser.setEmail(userDetails.getEmail());
        getUser.setPhoneNumber(userDetails.getPhoneNumber());
        getUser.setAddress(userDetails.getAddress());
        getUser.setSupervisor(userDetails.getSupervisor());
        getUser.setDepartment(userDetails.getDepartment());
        getUser.setTeam(userDetails.getTeam());
        getUser.setSchool(userDetails.getSchool());
        getUser.setLinkedinUrl(userDetails.getLinkedinUrl());
        getUser.setRole(Role.USER);

        var jwtToken = jwtService.generateToken(getUser);


        userRepository.save(getUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public Optional<UserModel> viewUserProfile(long id){

        return userRepository.findById(id);
    }

    public List<UserModel> getAllUsers(){

        return userRepository.findAll();
    }

//    public String verify(UserModel user){
//         Authentication authentication = authManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//         if(authentication.isAuthenticated())
//             return jwtService.generateToken(user.getUsername());
//
//         return "Failed";
//    }

//    public UserModel findUserById(Long id) throws ApiRequestException {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new ApiRequestException("User not found"));
//    }


}