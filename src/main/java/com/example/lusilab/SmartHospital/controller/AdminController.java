package com.example.lusilab.SmartHospital.controller;

import com.example.lusilab.SmartHospital.entity.Doctor;
import com.example.lusilab.SmartHospital.entity.Nurse;
import com.example.lusilab.SmartHospital.entity.Patient;
import com.example.lusilab.SmartHospital.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/doctors")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return adminService.createDoctor(doctor);
    }

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
        return adminService.getAllDoctors();
    }

    @PutMapping("/doctors/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return adminService.updateDoctor(id, doctor);
    }

    @DeleteMapping("/doctors/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        adminService.deleteDoctor(id);
    }

    @PostMapping("/nurses")
    public Nurse createNurse(@RequestBody Nurse nurse) {
        return adminService.createNurse(nurse);
    }

    @GetMapping("/nurses")
    public List<Nurse> getAllNurses() {
        return adminService.getAllNurses();
    }

    @PutMapping("/nurses/{id}")
    public Nurse updateNurse(@PathVariable Long id, @RequestBody Nurse nurse) {
        return adminService.updateNurse(id, nurse);
    }

    @DeleteMapping("/nurses/{id}")
    public void deleteNurse(@PathVariable Long id) {
        adminService.deleteNurse(id);
    }

    @PostMapping("/patients")
    public Patient createPatient(@RequestBody Patient patient) {
        return adminService.createPatient(patient);
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return adminService.getAllPatients();
    }

    @PutMapping("/patients/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return adminService.updatePatient(id, patient);
    }

    @DeleteMapping("/patients/{id}")
    public void deletePatient(@PathVariable Long id) {
        adminService.deletePatient(id);
    }
}