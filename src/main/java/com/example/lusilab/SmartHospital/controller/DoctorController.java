package com.example.lusilab.SmartHospital.controller;

import com.example.lusilab.SmartHospital.entity.Appointment;
import com.example.lusilab.SmartHospital.entity.Diagnosis;
import com.example.lusilab.SmartHospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{doctorId}/appointments")
    public String viewAppointments(@PathVariable Long doctorId, Model model) {
        List<Appointment> appointments = doctorService.getDoctorAppointments(doctorId);
        model.addAttribute("appointments", appointments);
        return "doctor/appointments";
    }

    @GetMapping("/appointment/{appointmentId}/diagnosis/add")
    public String showDiagnosisForm(@PathVariable Long appointmentId, Model model) {
        model.addAttribute("appointmentId", appointmentId);

        return "doctor/add-diagnosis";
    }

    @PostMapping("/appointment/{appointmentId}/diagnosis")
    public String addDiagnosis(@PathVariable Long appointmentId,
                               @RequestParam("description") String description) {
        Long patientId = doctorService.addDiagnosisAndReturnPatientId(appointmentId, description);

        return "redirect:/doctor/patient/" + patientId + "/history";
    }

    @GetMapping("/diagnosis/{diagnosisId}/prescription/add")
    public String showPrescriptionForm(@PathVariable Long diagnosisId, Model model) {
        model.addAttribute("diagnosisId", diagnosisId);
        return "doctor/add-prescription";
    }

    @PostMapping("/diagnosis/{diagnosisId}/prescription")
    public String addPrescription(@PathVariable Long diagnosisId,
                                  @RequestParam("medication") String medication,
                                  @RequestParam("dosage") String dosage) {
        Long patientId = doctorService.addPrescriptionAndReturnPatientId(diagnosisId, medication, dosage);

        return "redirect:/doctor/patient/" + patientId + "/history";
    }

    @GetMapping("/patient/{patientId}/history")
    public String viewMedicalHistory(@PathVariable Long patientId, Model model) {
        List<Diagnosis> history = doctorService.getPatientMedicalHistory(patientId);
        model.addAttribute("history", history);
        return "doctor/medical-history";
    }
}