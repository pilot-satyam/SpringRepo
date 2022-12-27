package com.ImyEye.info.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ImyEye.info.payloads.ApiResponse;
import com.ImyEye.info.payloads.UserDto;
import com.ImyEye.info.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//post - create user
	
	//creating this since we don't want to expose our thing directly from entity to out dto
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createdUserDto = this.userService.createUser(userDto);
		//response entity se hamara response jayega jaha user create hua yani createduserdto par aur status hamara jo create hua
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
	}
	
	//user details will be accepted with help of requestbody
	
	//put - update
	
	@PutMapping("/{userId}") //--> path uri variable
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId)
	{
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}
	
	//delete
	//only admin can delete

	/**
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	//Api Response class for returning the api response
	public ResponseEntity<ApiResponse> deleteUser(@Valid @PathVariable Integer userId)
	{
		this.userService.deleteUser(userId);
		return new ResponseEntity(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
	
	
	//get - get user ---> For all users
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		return ResponseEntity.ok(this.userService.getAllUsers()); 
	}
	
	//For single user
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId)
	{
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

}
