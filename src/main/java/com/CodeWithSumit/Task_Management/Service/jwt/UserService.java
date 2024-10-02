package com.CodeWithSumit.Task_Management.Service.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailService();
    
}
