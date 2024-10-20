package com.CodeWithSumit.Task_Management.Service.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import com.CodeWithSumit.Task_Management.Dto.TaskDTO;
import com.CodeWithSumit.Task_Management.Dto.UserDto;
import com.CodeWithSumit.Task_Management.Enums.TaskStatus;
import com.CodeWithSumit.Task_Management.Enums.UserRole;
import com.CodeWithSumit.Task_Management.Entity.Task;
import com.CodeWithSumit.Task_Management.Entity.User;
import com.CodeWithSumit.Task_Management.Repository.TaskRepository;
import com.CodeWithSumit.Task_Management.Repository.UserRepository;
import java.util.Comparator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;
    

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

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
       Optional<User> optionalUser = userRepository.findById(taskDTO.getEmployeeId());

       if(optionalUser.isPresent()){
        Task task =new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setDueDate(taskDTO.getDueDate());
        task.setTaskStatus(TaskStatus.INPROGRESS);
        task.setUser(optionalUser.get());
        return taskRepository.save(task).gettaskDTO();
       }
       return null;
       
    }

    @Override
    public List<TaskDTO> getallTask() {
       return taskRepository.findAll().stream().sorted(Comparator.comparing(Task::getDueDate).reversed()).map(Task::gettaskDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        
      taskRepository.deleteById(id);
    
    }

    @Override
    public TaskDTO getTaskById(Long id) {
      Optional<Task> optionalTask=taskRepository.findById(id);
      return optionalTask.map(Task::gettaskDTO).orElse(null);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
       Optional<Task> optionalTask=taskRepository.findById(id);
       Optional<User> optionalUser=userRepository.findById(taskDTO.getEmployeeId());

       if(optionalTask.isPresent()&& optionalUser.isPresent()){
        Task existingTask=optionalTask.get();
        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setDueDate(taskDTO.getDueDate());
        existingTask.setPriority(taskDTO.getPriority());
        existingTask.setTaskStatus(mapStringTotaskStatus(String.valueOf(taskDTO.getTaskStatus())));
        existingTask.setUser(optionalUser.get());
        return taskRepository.save(existingTask).gettaskDTO();
       }
       return null;

    }

    
    @Override
    public List<TaskDTO> searchTaskByTitle(String title) {
        return taskRepository.findAllByTitleContaining(title).stream().sorted(Comparator.comparing(Task::getDueDate).reversed())
        .map(Task::gettaskDTO)
        .collect(Collectors.toList());
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


}
