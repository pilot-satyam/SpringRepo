package com.ImyEye.info.payloads;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrescriptionDto {
    private Integer Id;
    private String OldRemarks;
    private String NewRemarks;
    private Date AddedDate;
	private String ReportImage;
    private DoctorDto doctor;
    private UserDto user;
}
