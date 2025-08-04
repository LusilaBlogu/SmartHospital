package com.example.lusilab.SmartHospital.controller;

import com.example.lusilab.SmartHospital.modelDTO.AppointmentDTO;
import com.example.lusilab.SmartHospital.modelDTO.PatientDTO;
import com.example.lusilab.SmartHospital.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/nurse")
public class NurseController {

    private final NurseService nurseService;

    @Autowired
    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }


    @GetMapping("/register-patient")
    public String showPatientForm(Model model) {
        model.addAttribute("patientDto", new PatientDTO());
        return "nurse/register-patient";
    }


    @PostMapping("/register-patient")
    public String registerPatient(@ModelAttribute("patientDto") PatientDTO patientDto, RedirectAttributes redirectAttributes) {
        try {
            nurseService.registerNewPatient(patientDto);
            redirectAttributes.addFlashAttribute("successMessage", "Pacienti u regjistrua me sukses!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/nurse/register-patient";
    }


    @GetMapping("/create-appointment")
    public String showAppointmentForm(Model model) {
        model.addAttribute("appointmentDto", new AppointmentDTO());
        return "nurse/create-appointment";
    }

    @PostMapping("/create-appointment")
    public String createAppointment(@ModelAttribute("appointmentDto") AppointmentDTO appointmentDto, RedirectAttributes redirectAttributes) {
        try {
            nurseService.createAppointment(appointmentDto);
            redirectAttributes.addFlashAttribute("successMessage", "Takimi u krijua me sukses!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Pati një gabim: " + e.getMessage());
        }
        return "redirect:/nurse/create-appointment";
    }

}