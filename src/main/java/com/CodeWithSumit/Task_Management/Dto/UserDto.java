package com.CodeWithSumit.Task_Management.Dto;

import com.CodeWithSumit.Task_Management.Enums.UserRole;

import lombok.Data;

@Data
public class UserDto {
	
	
    private Long id;

	private String name;
	
	private String email;
	
	private String password;

	
	private UserRole userRole;
	
	

}
