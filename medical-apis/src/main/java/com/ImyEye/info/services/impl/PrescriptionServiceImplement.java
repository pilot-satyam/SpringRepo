package com.ImyEye.info.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ImyEye.info.entities.Doctor;
import com.ImyEye.info.entities.Prescription;
import com.ImyEye.info.entities.User;
import com.ImyEye.info.exceptions.ResourceNotFoundException;
import com.ImyEye.info.payloads.PrescriptionDto;
import com.ImyEye.info.repositories.DoctorRepo;
import com.ImyEye.info.repositories.PrescriptionRepo;
import com.ImyEye.info.repositories.UserRepo;
import com.ImyEye.info.services.PrescriptionService;

@Service
public class PrescriptionServiceImplement implements PrescriptionService {
	
@Autowired
private PrescriptionRepo prescriptionRepo;
@Autowired
private ModelMapper modelMapper;
@Autowired
private UserRepo userRepo;
@Autowired
private DoctorRepo docRepo;	
	@Override
	public PrescriptionDto createPrescription(PrescriptionDto pdto, Integer userId, Integer DoctorId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		Doctor doc = this.docRepo.findById(DoctorId).orElseThrow(()->new ResourceNotFoundException("Doctor", "Doctor Id", DoctorId));
		//since hamare pdto mein sirf 2 methods hai to wahi aaya hoga yaha
		Prescription prescription =  this.modelMapper.map(pdto,Prescription.class);
		prescription.setReportImage("default.png");
		prescription.setAddedDate(new Date());
		prescription.setUser(user);
		prescription.setDoctor(doc);
		Prescription newPrescription =  this.prescriptionRepo.save(prescription);

		return this.modelMapper.map(newPrescription,PrescriptionDto.class);
	}

	@Override
	public PrescriptionDto updatePrescription(PrescriptionDto pDto, Integer Id) {
		Prescription prescription =  this.prescriptionRepo.findById(Id).orElseThrow(()->new ResourceNotFoundException("Prescription", "Id", Id));
		prescription.setOldRemarks(pDto.getOldRemarks());
		prescription.setNewRemarks(pDto.getNewRemarks());
		prescription.setReportImage(pDto.getReportImage());
		Prescription updatedPrescription =  this.prescriptionRepo.save(prescription);
		return this.modelMapper.map(updatedPrescription, PrescriptionDto.class);
	}

	@Override
	public void deletePrescription(Integer Id) {
		Prescription prescription = this.prescriptionRepo.findById(Id).orElseThrow(()->new ResourceNotFoundException("Prescription", "Id", Id));
		this.prescriptionRepo.delete(prescription);
	}

	@Override
	public PrescriptionDto getPrescriptionById(Integer Id) {
		Prescription prescription =  this.prescriptionRepo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("Prescription", "Id", Id));
		return this.modelMapper.map(prescription,PrescriptionDto.class); 
	}

	@Override
	public List<PrescriptionDto> getPrescriptionByDoctor(Integer DoctorId) {
		Doctor doc = this.docRepo.findById(DoctorId).orElseThrow(()-> new ResourceNotFoundException("Doctor", "Doctor Id", DoctorId));
		List<Prescription> prescriptions = this.prescriptionRepo.findByDoctor(doc);
		List<PrescriptionDto> prescriptionDtos =  prescriptions.stream().map((prescription)->this.modelMapper.map(prescription,PrescriptionDto.class)).collect(Collectors.toList());
		return prescriptionDtos;

	}

	@Override
	public List<PrescriptionDto> getPrescriptionByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user Id", userId));
		List<Prescription> prescriptions = this.prescriptionRepo.findByUser(user);
		List<PrescriptionDto> prescriptionDtos =  prescriptions.stream().map((prescription)->this.modelMapper.map(prescription,PrescriptionDto.class)).collect(Collectors.toList());
		return prescriptionDtos;
	}

	// @Override
	// public List<PrescriptionDto> searchPrescriptions(String keyword) {
	// 	List<Prescription> prescriptions =this.prescriptionRepo.searchByPrescriptions("%"+keyword+"%");
	// 	List<PrescriptionDto> prescriptionDtos = prescriptions.stream().map((prescription)->modelMapper.map(prescription,PrescriptionDto.class)).collect((Collectors.toList()));
	// 	return prescriptionDtos;
	// }

}
