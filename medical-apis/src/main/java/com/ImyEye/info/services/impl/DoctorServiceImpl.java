package com.ImyEye.info.services.impl;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ImyEye.info.entities.Doctor;
import com.ImyEye.info.exceptions.ResourceNotFoundException;
import com.ImyEye.info.payloads.DoctorDto;
import com.ImyEye.info.services.DoctorService;
import com.ImyEye.info.repositories.DoctorRepo;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired 
	private DoctorRepo doctorRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public DoctorDto createDoctor(DoctorDto doctorDto) {
		
		Doctor doctor = this.dtoToDoctor(doctorDto);
		Doctor savedDoctor = this.doctorRepo.save(doctor);
		return this.doctorToDto(savedDoctor);
	}

	@Override
	public DoctorDto updateDoctor(DoctorDto doctorDto, Integer DoctorId) {
		        //here id is the field name and userId is it's value
				//first getting 
				Doctor doctor = this.doctorRepo.findById(DoctorId).orElseThrow(()->new ResourceNotFoundException("Doctor","Id",DoctorId));
				//after getting now we are setting
				doctor.setName(doctorDto.getName());
				doctor.setEmail(doctorDto.getEmail());
				doctor.setPassword(doctorDto.getPassword());
				//now update it
				Doctor updatedDoc = doctorRepo.save(doctor);
				DoctorDto docDto1 = doctorToDto(updatedDoc);
				return docDto1;
	
	}

	@Override
	public DoctorDto getDoctorById(Integer DoctorId) {
		Doctor doc = this.doctorRepo.findById(DoctorId).orElseThrow(()->new ResourceNotFoundException("Doctor","Id",DoctorId));
		return this.doctorToDto(doc);
	}

	@Override
	public List<DoctorDto> getAllDoctors() {
		//we are using Lambda's stream api to convert List of users to List of UserDto
				List<Doctor> doctors = this.doctorRepo.findAll();
				//since we need to send UserDto hence we need to convert users into 
				List<DoctorDto> doctorDtos = doctors.stream().map(doctor->this.doctorToDto(doctor)).collect(Collectors.toList());
				return doctorDtos;
	}

	
	public Doctor dtoToDoctor(DoctorDto doctorDto)
	{
		//using model mapper we give src to destination
		
		Doctor doctor = this.modelMapper.map(doctorDto, Doctor.class);
		return doctor;
	}
	
	//this method is converting the doctor entity to DoctorDto ---> vice-versa of above method
	
	public DoctorDto doctorToDto(Doctor doctor)
	{
		DoctorDto doctorDto = this.modelMapper.map(doctor,DoctorDto.class);
		return doctorDto;
	}

}
