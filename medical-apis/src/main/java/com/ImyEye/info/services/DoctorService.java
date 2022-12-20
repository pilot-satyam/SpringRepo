package com.ImyEye.info.services;

import java.util.List;

import com.ImyEye.info.payloads.DoctorDto;


public interface DoctorService {

	DoctorDto createDoctor(DoctorDto doctor);
	DoctorDto updateDoctor(DoctorDto doctorDto,Integer DoctorId);
	DoctorDto getDoctorById(Integer DoctorId);
	List<DoctorDto> getAllDoctors();
}
