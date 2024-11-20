package com.Oreoluwa.Tracker.API.repository;


import com.Oreoluwa.Tracker.API.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByPhoneNumber(String phoneNumber);

    UserModel findByUsername(String username);

//Optional<UserModel> findById(userId);


//    Optional<UserModel> findById(Long id);
}