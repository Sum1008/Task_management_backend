package com.CodeWithSumit.Task_Management.Service.admin;

import java.util.List;

import com.CodeWithSumit.Task_Management.Dto.CommentDTO;
import com.CodeWithSumit.Task_Management.Dto.TaskDTO;
import com.CodeWithSumit.Task_Management.Dto.UserDto;

public interface AdminService {


    List<UserDto> getUsers();

    TaskDTO createTask(TaskDTO taskDTO);

    List<TaskDTO> getallTask();

    void deleteTask(Long id);

    TaskDTO getTaskById(Long id);

    TaskDTO updateTask(Long id,TaskDTO taskDTO);

    List<TaskDTO> searchTaskByTitle(String titile);
 
    CommentDTO createComment(Long taskId,String content);

    List<CommentDTO> getCommentByTaskId(Long taskId);

}
