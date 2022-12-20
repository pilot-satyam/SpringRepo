package com.ImyEye.info.entities;

import java.util.Date;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Prescription {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer Id;
	private String oldRemarks;
	private String NewRemarks;
	private Date AddedDate;
	
//	private User userId;
//	@ManyToOne
//	private Doctor doctor;
}
