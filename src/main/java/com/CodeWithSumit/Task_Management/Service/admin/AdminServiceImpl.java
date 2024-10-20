package com.CodeWithSumit.Task_Management.Service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.CodeWithSumit.Task_Management.Dto.UserDto;
import com.CodeWithSumit.Task_Management.Enums.UserRole;
import com.CodeWithSumit.Task_Management.Entity.User;
import com.CodeWithSumit.Task_Management.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsers() {
        // Fetch all users from the repository, filter by EMPLOYEE role, and map to UserDto
        return userRepository.findAll() // List<User> is returned by findAll()
            .stream() // Convert List<User> to Stream<User>
            .filter(user -> user.getUserRole() == UserRole.EMPLOYEE) // Filter by UserRole.EMPLOYEE
            .map(this::convertToUserDto) // Convert each User to UserDto
            .collect(Collectors.toList()); // Collect the results into a List<UserDto>
    }

    // Method to convert User entity to UserDto
    private UserDto convertToUserDto(User user) {
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getUserRole()
        );
    }
}
