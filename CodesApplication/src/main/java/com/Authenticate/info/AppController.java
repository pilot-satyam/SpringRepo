package com.Authenticate.info;
import org.springframework.ui.Model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

	@Autowired
	private UserRepo repo;
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	@GetMapping("/register")
	public String showSignUp(Model model) {
		model.addAttribute("user",new User());
		return "signUp";
	}
	
	//TO accept the response from submit button in registration form and connect DB
	@PostMapping("/process_register")
	public String processRegistration(User user)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repo.save(user);
		return "register_success";
	}
	
	@GetMapping("/list_users")
	public String viewUserList(Model model)
	{
		List<User> listUsers = repo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users"; 
	}
}
