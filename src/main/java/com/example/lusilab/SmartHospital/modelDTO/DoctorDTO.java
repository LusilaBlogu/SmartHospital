package com.example.lusilab.SmartHospital.modelDTO;

import com.example.lusilab.SmartHospital.entity.DoctorRole;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DoctorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String specialization;
    private DoctorRole doctorRole;

    public DoctorDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public DoctorRole getDoctorRole() { return doctorRole; }
    public void setDoctorRole(DoctorRole doctorRole) { this.doctorRole = doctorRole; }
}