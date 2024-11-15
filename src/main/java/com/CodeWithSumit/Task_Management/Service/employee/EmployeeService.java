package com.CodeWithSumit.Task_Management.Service.employee;

import java.util.List;

import com.CodeWithSumit.Task_Management.Dto.TaskDTO;

public interface EmployeeService {


    List<TaskDTO> getTaskByUserId();

    TaskDTO updateTask(Long id, String status);
    
    
}
