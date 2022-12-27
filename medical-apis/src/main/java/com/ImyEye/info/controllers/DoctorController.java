package com.ImyEye.info.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;	
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ImyEye.info.payloads.DoctorDto;
import com.ImyEye.info.services.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	//post - create user

	@PostMapping("/")
	public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto doctorDto){
		DoctorDto createdDoctorDto = this.doctorService.createDoctor(doctorDto);
		return new ResponseEntity<>(createdDoctorDto,HttpStatus.CREATED);
	}
	
	//user details will be accepted with help of requestbody
	
	//put - update
	
	@PutMapping("/{DoctorId}") //--> path uri variable
	public ResponseEntity<DoctorDto> updateUser(@Valid @RequestBody DoctorDto doctorDto,@PathVariable Integer DoctorId)
	{
		DoctorDto updatedDoc = this.doctorService.updateDoctor(doctorDto, DoctorId);
		return ResponseEntity.ok(updatedDoc);
	}
	
	//delete
	
	//get - get user ---> For all users
	
	@GetMapping("/")
	public ResponseEntity<List<DoctorDto>> getAllDoctors()
	{
		return ResponseEntity.ok(this.doctorService.getAllDoctors()); 
	}
	
	//For single user
	
	@GetMapping("/{DoctorId}")
	public ResponseEntity<DoctorDto> getSingleDoctor(@PathVariable Integer DoctorId)
	{
		return ResponseEntity.ok(this.doctorService.getDoctorById(DoctorId));
	}

	
}
