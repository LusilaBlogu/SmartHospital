package com.example.lusilab.SmartHospital.controller;

import com.example.lusilab.SmartHospital.entity.Appointment;
import com.example.lusilab.SmartHospital.entity.Diagnosis;
import com.example.lusilab.SmartHospital.entity.Patient;
import com.example.lusilab.SmartHospital.entity.Prescription;
import com.example.lusilab.SmartHospital.modelDTO.PatientDTO;
import com.example.lusilab.SmartHospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{patientId}/appointments")
    public String viewAppointments(@PathVariable Long patientId, Model model) {
        List<Appointment> appointments = patientService.getAppointments(patientId);
        model.addAttribute("appointments", appointments);
        model.addAttribute("patient", patientService.getPatientById(patientId));
        return "patient/appointments";
    }

    @GetMapping("/{patientId}/diagnoses")
    public String viewDiagnoses(@PathVariable Long patientId, Model model) {
        List<Diagnosis> diagnoses = patientService.getDiagnoses(patientId);
        model.addAttribute("diagnoses", diagnoses);
        model.addAttribute("patient", patientService.getPatientById(patientId));
        return "patient/diagnoses";
    }

    @GetMapping("/{patientId}/prescriptions")
    public String viewPrescriptions(@PathVariable Long patientId, Model model) {
        List<Prescription> prescriptions = patientService.getPrescriptions(patientId);
        model.addAttribute("prescriptions", prescriptions);
        model.addAttribute("patient", patientService.getPatientById(patientId));
        return "patient/prescriptions";
    }

    @GetMapping("/{patientId}/edit")
    public String showEditForm(@PathVariable Long patientId, Model model) {

        Patient patient = patientService.getPatientById(patientId);

        PatientDTO patientDto = new PatientDTO();
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setEmail(patient.getEmail());
        patientDto.setGender(patient.getGender());
        patientDto.setBirthDate(patient.getBirthDate());

        model.addAttribute("patientDto", patientDto);
        model.addAttribute("patientId", patientId);
        return "patient/edit-profile";
    }

    @PostMapping("/{patientId}/edit")
    public String updatePatient(@PathVariable Long patientId,
                                @ModelAttribute("patientDto") PatientDTO patientDto,
                                RedirectAttributes redirectAttributes) {
        try {
            patientService.updatePatient(patientId, patientDto);
            redirectAttributes.addFlashAttribute("successMessage", "Profili u përditësua me sukses!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Pati një gabim gjatë përditësimit.");
        }

        return "redirect:/patient/" + patientId + "/appointments";
    }
}