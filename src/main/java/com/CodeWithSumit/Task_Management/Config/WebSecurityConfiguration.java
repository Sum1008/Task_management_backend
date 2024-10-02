package com.CodeWithSumit.Task_Management.Config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.CodeWithSumit.Task_Management.Enums.UserRole;
import com.CodeWithSumit.Task_Management.Service.jwt.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    // Define the security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Disable CSRF protection and configure the request authorization
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Allow everyone to access the auth endpoints
                .requestMatchers("/api/admin/**").hasAuthority(UserRole.ADMIN.name()) // Admin endpoints restricted to ADMIN role
                .requestMatchers("/api/employee/**").hasAuthority(UserRole.EMPLOYEE.name()) // Employee endpoints restricted to EMPLOYEE role
                .anyRequest().authenticated()) // All other requests must be authenticated
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) // Use stateless sessions (JWT)
            .authenticationProvider(authenticationProvider()) // Use the custom authentication provider
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add the JWT filter before UsernamePasswordAuthenticationFilter

        return http.build();
    }

    // Authentication provider bean to manage how authentication is handled
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailService()); // Use custom UserDetailsService for loading user details
        authProvider.setPasswordEncoder(passwordEncoder()); // Use bcrypt password encoder
        return authProvider;
    }

    // Password encoder bean to encrypt and verify passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt encoder for hashing passwords
    }

    // Bean to define the authentication manager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManager.class);
    }
}
