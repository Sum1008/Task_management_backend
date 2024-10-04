package com.CodeWithSumit.Task_Management.Entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.CodeWithSumit.Task_Management.Dto.UserDto;
import com.CodeWithSumit.Task_Management.Enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.List;

@Entity
@Data

public class User implements UserDetails {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;

    private String email;

    private String password;

    private UserRole userRole;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override 
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;

    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    
    public UserDto getUserDto() {
    	UserDto userDto =new UserDto();
    	userDto.setId(id);
    	userDto.setName(name);
    	userDto.setEmail(email);
    	userDto.setUserRole(userRole);
    	return userDto;
    	
    }

    


}
