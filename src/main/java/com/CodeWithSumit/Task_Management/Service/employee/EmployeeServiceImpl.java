package com.CodeWithSumit.Task_Management.Service.employee;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.CodeWithSumit.Task_Management.Dto.CommentDTO;
import com.CodeWithSumit.Task_Management.Dto.TaskDTO;
import com.CodeWithSumit.Task_Management.Entity.Comment;
import com.CodeWithSumit.Task_Management.Entity.Task;
import com.CodeWithSumit.Task_Management.Entity.User;
import com.CodeWithSumit.Task_Management.Enums.TaskStatus;
import com.CodeWithSumit.Task_Management.Repository.TaskRepository;
import com.CodeWithSumit.Task_Management.Utiles.JwtUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {


    private final TaskRepository taskRepository;

    private final JwtUtil jwtUtil;
    
    @Override
    public List<TaskDTO> getTaskByUserId() {
       User user= jwtUtil.getLoggedInUser();
       if(user !=null){
        return taskRepository.finAllByUserId(user.getId())
        .stream()
        .sorted(Comparator.comparing(Task::getDueDate).reversed())
        .map(Task::getTaskDTO)
        .collect(Collectors.toList());

       }
       throw new EntityNotFoundException("User not found ");


    
    }

    @Override
    public TaskDTO updateTask(Long id, String status) {
        Optional<Task> optionalTask =taskRepository.findById(id);
        
        if(optionalTask.isPresent()){
            Task existingTask = optionalTask.get();
            existingTask.setTaskStatus(mapStringTotaskStatus(status));
            return taskRepository.save(existingTask).gettaskDTO();
        }
        throw new EntityNotFoundException("Task not found ");
    }
    
    private TaskStatus mapStringTotaskStatus(String status){
        return switch(status){
            case "PENDING" -> TaskStatus.PENDING;
            case "INPROGRESS" -> TaskStatus.INPROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELLED;


        };
    }

    @Override
    public TaskDTO getTaskById(Long id) {
      Optional<Task> optionalTask=taskRepository.findById(id);
      return optionalTask.map(Task::gettaskDTO).orElse(null);
    }

    
    @Override
    public CommentDTO createComment(Long taskId, String content) {
       Optional<Task> optionalTask=taskRepository.findById(taskId);
       User user=jwtUtil.getLoggedInUser();
       if((optionalTask.isPresent()) && user != null){
        Comment comment =new Comment();
        comment.setCreatedAT(new Date());
        comment.setContent(content);
        comment.setTask(optionalTask.get());
        comment.setUser(user);
        return commentRepository.save(comment).getCommentDTO();

       }

       throw new EntityNotFoundException("User or Task not found");
    }

    @Override
    public List<CommentDTO> getCommentByTaskId(Long taskId) {
       return commentRepository.findAllByTaskId(taskId).stream().map(Comment::getCommentDTO).collect(Collectors.toList());

    }

}
