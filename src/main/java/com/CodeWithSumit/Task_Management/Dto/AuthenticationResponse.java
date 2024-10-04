package com.CodeWithSumit.Task_Management.Dto;

import com.CodeWithSumit.Task_Management.Enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
	
	private String jwt;
	
	private Long userId;
	
	private  UserRole  userRole;
	
	

}
