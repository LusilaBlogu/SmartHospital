package com.example.lusilab.SmartHospital.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String specialization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DoctorRole doctorRole;

    public Doctor() {}

    public Doctor(String firstName, String lastName, String email, String password, String specialization, DoctorRole doctorRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.specialization = specialization;
        this.doctorRole = doctorRole;
    }

    // Getters dhe Setters (Mbeten të njëjta)
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

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", specialization='" + specialization + '\'' +
                ", doctorRole=" + doctorRole +
                '}';
    }
}