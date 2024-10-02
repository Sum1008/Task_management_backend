package com.CodeWithSumit.Task_Management.Service.auth;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.CodeWithSumit.Task_Management.Entity.User;
import com.CodeWithSumit.Task_Management.Enums.UserRole;
import com.CodeWithSumit.Task_Management.Repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceimpl implements Authservice  {


    private final UserRepository userRepository;
    

    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> optionalUser=userRepository.findByUserRole(UserRole.ADMIN);

        if(optionalUser.isEmpty()){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("Admin account created successfully");
        }else{
            System.out.println("Admin account exist ");
        }

    }

}
