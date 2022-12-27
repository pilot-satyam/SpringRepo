package com.ImyEye.info.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

import com.ImyEye.info.entities.Doctor;
import com.ImyEye.info.entities.Prescription;
import com.ImyEye.info.entities.User;


public interface PrescriptionRepo extends JpaRepository<Prescription, Integer> {

    List<Prescription> findByUser(User user);
    List<Prescription> findByDoctor(Doctor doctor);
    //to search patients by new remarks means to see how many people are affected by covid
    // List<Prescription> findBynewRemarksContaining(String newRemarks);
    // @Query("select p from Prescription p where p.newRemarks like : key")
    // List<Prescription> searchByPrescriptions(@Param("key") String prescriptions);
}
