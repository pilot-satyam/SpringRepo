package com.ImyEye.info.services;

import java.util.List;
import com.ImyEye.info.payloads.PrescriptionDto;

public interface PrescriptionService {

   //create
    PrescriptionDto createPrescription(PrescriptionDto pdto,Integer userId,Integer DoctorId); //doc will create
    //update ---> By Doc
    PrescriptionDto updatePrescription(PrescriptionDto pDto,Integer Id);
    //delete
    void deletePrescription(Integer Id);

    //get Single Prescription Of Patient
    PrescriptionDto getPrescriptionById(Integer Id);
    
    //get all patients of Single doc
    List<PrescriptionDto> getPrescriptionByDoctor(Integer DoctorId);

    //get all users prescription
    List<PrescriptionDto> getPrescriptionByUser(Integer userId);

    //search doctors based on their speciality

    //  List<PrescriptionDto> searchPrescriptions(String keyword); 
}
