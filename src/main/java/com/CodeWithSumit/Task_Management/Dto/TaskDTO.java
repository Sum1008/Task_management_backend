package com.CodeWithSumit.Task_Management.Dto;

import com.CodeWithSumit.Task_Management.Enums.TaskStatus;

import jakarta.annotation.sql.DataSourceDefinition;
import lombok.Data;
import java.util.*;
@Data
public class TaskDTO {

   private Long id;

    private String title;

    private String description;

    private Date dueDate;

    private String priority;

    private TaskStatus taskStatus;

    private Long employeeId;

    private String employeeName;


    

}
