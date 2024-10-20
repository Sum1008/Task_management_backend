package com.CodeWithSumit.Task_Management.Dto;

import com.CodeWithSumit.Task_Management.Enums.UserRole;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
	
	
    private Long id;

	private String name;
	
	private String email;
	
	private String password;

	
	private UserRole userRole;
	public UserDto(Long id, String name, String email, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userRole = userRole;
    }
	

}
