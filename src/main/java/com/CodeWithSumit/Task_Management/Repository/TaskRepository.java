package com.CodeWithSumit.Task_Management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CodeWithSumit.Task_Management.Dto.TaskDTO;
import com.CodeWithSumit.Task_Management.Entity.Task;
import java.util.*;
@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findAllByTitleContaining(String title);

}
