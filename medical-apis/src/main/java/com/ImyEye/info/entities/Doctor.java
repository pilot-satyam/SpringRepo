package com.ImyEye.info.entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="doctors")
@NoArgsConstructor
public class Doctor {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
     private int DoctorId;	

	@Column(nullable = false,length=100)
     private String name;
     private String email;
     private String password;
     
     @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
     List<Prescription> lists = new ArrayList<>();

     // @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
     // @JoinTable(name="doctor_role",joinColumns = @JoinColumn(name="doctor",referencedColumnName = "DoctorId"),
     // inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "DoctorId"))
     // private Set<Role> roles = new HashSet<>();
}
