package com.CodeWithSumit.Task_Management.Controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CodeWithSumit.Task_Management.Dto.CommentDTO;
import com.CodeWithSumit.Task_Management.Dto.TaskDTO;
import com.CodeWithSumit.Task_Management.Service.admin.AdminService;
import java.util.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(adminService.getUsers());
        
    }
     

    @PostMapping("/task")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
        TaskDTO createdTaskDTO =adminService.createTask(taskDTO);
        if(createdTaskDTO==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            return  ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDTO);

        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getallTask(){
        return ResponseEntity.ok(adminService.getallTask());
        
    }


    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        adminService.deleteTask(id);
        return ResponseEntity.ok(null);


    }
     
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getTaskById(id));

    }

    @PutMapping("/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        TaskDTO updatedTask =adminService.updateTask(id, taskDTO);
        if(updatedTask == null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedTask);

    }

    @GetMapping("/task/search/{title}")
    public ResponseEntity<List<TaskDTO>> searchTask(@PathVariable String title){
        return ResponseEntity.ok(adminService.searchTaskByTitle(title));

    }

    @PostMapping("/task/comment/{taskId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long taskId,@RequestParam String content){
        CommentDTO createdCommentDTO =adminService.createComment(taskId,content);
        if(createdCommentDTO==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            return  ResponseEntity.status(HttpStatus.CREATED).body(createdCommentDTO);

        }
    }


    @GetMapping("/comments/{taskId}")
    public ResponseEntity<List<CommentDTO>>getCommentsByTakId(@PathVariable Long taskId){
        return ResponseEntity.ok(adminService.getCommentByTaskId(taskId));
    }

}
