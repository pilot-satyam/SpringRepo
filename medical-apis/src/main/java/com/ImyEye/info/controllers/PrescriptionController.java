package com.ImyEye.info.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ImyEye.info.payloads.ApiResponse;
import com.ImyEye.info.payloads.PrescriptionDto;
import com.ImyEye.info.services.FileService;
import com.ImyEye.info.services.PrescriptionService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;
    
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //create
    @PostMapping("/user/{userId}/doctor/{DoctorId}/prescriptions")
    public ResponseEntity<PrescriptionDto> createPrescription(
        @RequestBody PrescriptionDto pdto,
        @PathVariable Integer userId,
        @PathVariable Integer DoctorId
        )

    {
         PrescriptionDto createPrescription = this.prescriptionService.createPrescription(pdto, userId, DoctorId);
         return new ResponseEntity<PrescriptionDto>(createPrescription,HttpStatus.CREATED);    
    }

    //get by doctors
    @GetMapping("/doctor/{DoctorId}/prescriptions")
    public ResponseEntity<List<PrescriptionDto>> getPrescriptionByDoctor(
        @PathVariable Integer DoctorId
    )
    {
        List<PrescriptionDto> prescriptions =  this.prescriptionService.getPrescriptionByDoctor(DoctorId);
        return new ResponseEntity<List<PrescriptionDto>>(prescriptions,HttpStatus.OK);
    }

    //getByUsers
    @GetMapping("/user/{userId}/prescriptions")
    public ResponseEntity<List<PrescriptionDto>> getPrescriptionByUser(
        @PathVariable Integer userId
    )
    {
        List<PrescriptionDto> prescriptions =  this.prescriptionService.getPrescriptionByUser(userId);
        return new ResponseEntity<List<PrescriptionDto>>(prescriptions,HttpStatus.OK);
    }

    //get prescription detail by id
    @GetMapping("/prescriptions/{Id}")
    public ResponseEntity<PrescriptionDto> getPrescription(@PathVariable Integer Id)
    {
        PrescriptionDto prescriptionDto =  this.prescriptionService.getPrescriptionById(Id);
        return new ResponseEntity<PrescriptionDto>(prescriptionDto,HttpStatus.OK);
    }

    //delete prescription
    @DeleteMapping("/prescriptions/{Id}")
    public ApiResponse deletePrescription(@PathVariable Integer Id)
    {
        this.prescriptionService.deletePrescription(Id);
        return new ApiResponse("Prescription Successfully deleted",true);
    }

    //update Prescription
    @PutMapping("/prescriptions/{pId}")
    public ResponseEntity<PrescriptionDto> updatePrescription(@RequestBody PrescriptionDto pDto,@PathVariable Integer pId)
    {
      PrescriptionDto updatedPrescriptionDto =  this.prescriptionService.updatePrescription(pDto, pId);
      return new ResponseEntity<PrescriptionDto>(updatedPrescriptionDto,HttpStatus.OK);
    }

    // //search
    // @GetMapping("/prescriptions/search/{keywords}")
    // public ResponseEntity<List<PrescriptionDto>> searchPrescriptionByNewRemarks(
    //     @PathVariable("keywords") String keywords
    // ){
    //     List<PrescriptionDto> searchedPrescription =  this.prescriptionService.searchPrescriptions(keywords);
    //     return new ResponseEntity<List<PrescriptionDto>>(searchedPrescription,HttpStatus.OK);
    // }



    //Post Image Upload
    //Path variable is used to fetch the prescription Id
    /**
     * @param image
     * @param Id
     * @return
     * @throws IOException
     */
    @PostMapping("/prescription/image/upload/{Id}")
    public ResponseEntity<PrescriptionDto> uploadReportImage(
        @RequestParam("image") MultipartFile image,
        @PathVariable Integer Id
    ) throws IOException
    {
       PrescriptionDto prescriptionDto = this.prescriptionService.getPrescriptionById(Id);
       String fileName =   this.fileService.uploadImage(path, image);
       prescriptionDto.setReportImage(fileName);
       PrescriptionDto updatedPrescription = this.prescriptionService.updatePrescription(prescriptionDto, Id);
       return new ResponseEntity<PrescriptionDto>(updatedPrescription,HttpStatus.OK);
    }


    //method to serve files
    @GetMapping(value = "/prescription/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable ("imageName") String imageName,
        HttpServletResponse response
    )throws IOException{
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
