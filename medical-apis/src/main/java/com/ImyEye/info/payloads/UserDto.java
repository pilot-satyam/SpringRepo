package com.ImyEye.info.payloads;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {

	private int id;
	@NotEmpty
	@Size(min = 4, message = "Name must be greater than 4 characters")
	private String name;
	@Email(message="Email Not valid")
	private String email;
	@NotEmpty
	@Size(min=3,max=10,message = "Password must be minimum of 3 chars and max of 10 chars")
	private String password;
	private Set<RoleDto> roles = new HashSet<>();

}
