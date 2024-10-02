package com.CodeWithSumit.Task_Management.Config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.CodeWithSumit.Task_Management.Service.jwt.UserService;
import com.CodeWithSumit.Task_Management.Utiles.JwtUtil;

import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                   @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) 
                                    throws ServletException, IOException {

        // 1. Extract the Authorization header
        final String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
         String userEmail = null;

        // 2. Check if the Authorization header contains a valid Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
           filterChain.doFilter(request, response);
           return ;
        }
        jwtToken=authHeader.substring(7);
        userEmail=jwtUtil.extractUserName(jwtToken);

        // 4. Validate token and authenticate user
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details from the database
            UserDetails userDetails = userService.userDetailService().loadUserByUsername(userEmail);

            // 5. Check if the token is valid
            if (jwtUtil.isTokenValid(jwtToken, userDetails)) {
                // If valid, set authentication in the context
                SecurityContext context=SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
