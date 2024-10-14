package com.Oreoluwa.Tracker.API.domain.response;

import com.Oreoluwa.Tracker.API.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserResponse {
    private String status;
    private String message;

}