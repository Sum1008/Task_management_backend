package com.CodeWithSumit.Task_Management.Controller.employee;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CodeWithSumit.Task_Management.Dto.CommentDTO;
import com.CodeWithSumit.Task_Management.Dto.TaskDTO;
import com.CodeWithSumit.Task_Management.Service.employee.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getTaskByUserId(){
        return ResponseEntity.ok(employeeService.getTaskByUserId());

    }

    @GetMapping("/task/{id}/{status}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable long id , @PathVariable String status ){
        TaskDTO updateTaskDTO= employeeService.updateTask(null, status);
        if(updateTaskDTO==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
        return ResponseEntity.ok(updateTaskDTO);

    }

    
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.getTaskById(id));

    }

    @PostMapping("/task/comment/{taskId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long taskId,@RequestParam String content){
        CommentDTO createdCommentDTO =employeeService.createComment(taskId,content);
        if(createdCommentDTO==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            return  ResponseEntity.status(HttpStatus.CREATED).body(createdCommentDTO);

        }
    }

    @GetMapping("/comments/{taskId}")
    public ResponseEntity<List<CommentDTO>>getCommentsByTakId(@PathVariable Long taskId){
        return ResponseEntity.ok(employeeService.getCommentByTaskId(taskId));
    }

}
