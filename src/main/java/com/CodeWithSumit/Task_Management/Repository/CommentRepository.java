package com.CodeWithSumit.Task_Management.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CodeWithSumit.Task_Management.Dto.CommentDTO;
import com.CodeWithSumit.Task_Management.Entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByTaskId(Long taskId);

}
