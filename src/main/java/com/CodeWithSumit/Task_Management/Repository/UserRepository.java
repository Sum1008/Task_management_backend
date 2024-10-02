package com.CodeWithSumit.Task_Management.Repository;



import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CodeWithSumit.Task_Management.Entity.User;
import com.CodeWithSumit.Task_Management.Enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findFirstByEmail(String username);

    Optional<User> findByUserRole(UserRole admin);


  
}
