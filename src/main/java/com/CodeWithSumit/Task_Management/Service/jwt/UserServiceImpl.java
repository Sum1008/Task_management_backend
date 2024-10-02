package com.CodeWithSumit.Task_Management.Service.jwt;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.CodeWithSumit.Task_Management.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
                return userRepository.findFirstByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
            }

        
        };
    }

    
}
