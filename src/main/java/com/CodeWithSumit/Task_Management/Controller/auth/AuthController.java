package com.CodeWithSumit.Task_Management.Controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CodeWithSumit.Task_Management.Dto.AuthenticationRequest;
import com.CodeWithSumit.Task_Management.Dto.AuthenticationResponse;
import com.CodeWithSumit.Task_Management.Dto.SignupRequest;
import com.CodeWithSumit.Task_Management.Dto.UserDto;
import com.CodeWithSumit.Task_Management.Entity.User;
import com.CodeWithSumit.Task_Management.Repository.UserRepository;
import com.CodeWithSumit.Task_Management.Service.auth.Authservice;
import com.CodeWithSumit.Task_Management.Service.jwt.UserService;
import com.CodeWithSumit.Task_Management.Utiles.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserRepository userRepository;
	
	private final Authservice authService;
	
	private final JwtUtil jwtUtil;
	
	private final UserService userService;
	
	private final AuthenticationManager authenticationManager;
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
		if(authService.hasUserWithEmail(signupRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exist with this email");
			
		}
		UserDto createdUserDto=authService.signupUser(signupRequest);
		if(createdUserDto==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
			
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
		
	}
	
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
					
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password");
			
		}
		final UserDetails userDetails= userService.userDetailService().loadUserByUsername(authenticationRequest.getEmail());
		Optional<User> optionalUser =userRepository.findFirstByEmail(authenticationRequest.getEmail());
		final String jwtToken=jwtUtil.generateToken(userDetails);
		
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		if(optionalUser.isPresent()) {
			authenticationResponse.setJwt(jwtToken);
			authenticationResponse.setUserId(optionalUser.get().getId());
			authenticationResponse.setUserRole(optionalUser.get().getUserRole());
			
		}
		return authenticationResponse;
		
	}
	

}
