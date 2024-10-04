package com.CodeWithSumit.Task_Management.Service.auth;

import com.CodeWithSumit.Task_Management.Dto.SignupRequest;
import com.CodeWithSumit.Task_Management.Dto.UserDto;

public interface Authservice {
    void createAnAdminAccount();  // Define method

    UserDto signupUser(SignupRequest signupRequest);
    
    boolean hasUserWithEmail(String email);
    
}
