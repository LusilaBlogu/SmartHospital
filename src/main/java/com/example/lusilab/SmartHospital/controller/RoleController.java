package com.example.lusilab.SmartHospital.controller;

import com.example.lusilab.SmartHospital.entity.Admin;
import com.example.lusilab.SmartHospital.service.AdminService;
import com.example.lusilab.SmartHospital.service.DoctorService;
import com.example.lusilab.SmartHospital.service.NurseService;
import com.example.lusilab.SmartHospital.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class RoleController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private NurseService nurseService;
    @Autowired
    private PatientService patientService;

    @GetMapping("/")
    public String redirectToChooseRole() {
        return "redirect:/choose-role";
    }

    @GetMapping("/choose-role")
    public String showChooseRolePage(Model model) {
        model.addAttribute("pageTitle", "SmartHospital - Choose Role");
        return "choose-role";
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam String role, Model model) {
        model.addAttribute("role", role);
        model.addAttribute("admin", new Admin());
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("admin") Admin admin,
                              @RequestParam String role,
                              RedirectAttributes redirectAttributes,
                              HttpServletRequest request,
                              Model model) {

        Optional<? extends Admin> loggedInUser;

        switch (role.toLowerCase()) {
            case "admin" -> loggedInUser = adminService.login(admin.getEmail(), admin.getPassword());
            case "doctor" -> loggedInUser = doctorService.login(admin.getEmail(), admin.getPassword());
            case "nurse" -> loggedInUser = nurseService.login(admin.getEmail(), admin.getPassword());
            case "patient" -> loggedInUser = patientService.login(admin.getEmail(), admin.getPassword());
            default -> loggedInUser = Optional.empty();
        }

        if (loggedInUser.isPresent()) {
            Admin authenticatedUser = loggedInUser.get();

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            authenticatedUser,
                            null,
                            List.of(new SimpleGrantedAuthority(role.substring(0, 1).toUpperCase() + role.substring(1)))
                    );

            SecurityContextHolder.getContext().setAuthentication(authToken);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return "redirect:/" + role + "/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Email or password is incorrect.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/login?role=" + role;
        }
    }

    @GetMapping("/signup")
    public String showSignupPage(@RequestParam String role, Model model) {
        model.addAttribute("role", role);
        model.addAttribute("admin", new Admin());
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute("admin") Admin admin,
                               @RequestParam String role,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (admin.getName() == null || admin.getName().isEmpty() ||
                admin.getUsername() == null || admin.getUsername().isEmpty() ||
                admin.getEmail() == null || admin.getEmail().isEmpty() ||
                admin.getPassword() == null || admin.getPassword().isEmpty()) {

            redirectAttributes.addFlashAttribute("error", "Please fill in all fields.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/signup?role=" + role;
        }

        boolean exists;
        switch (role.toLowerCase()) {
            case "admin" -> exists = adminService.existsByEmail(admin.getEmail());
            case "doctor" -> exists = doctorService.existsByEmail(admin.getEmail());
            case "nurse" -> exists = nurseService.existsByEmail(admin.getEmail());
            case "patient" -> exists = patientService.existsByEmail(admin.getEmail());
            default -> exists = true;
        }

        if (exists) {
            redirectAttributes.addFlashAttribute("error", "This email already exists.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/signup?role=" + role;
        }

        switch (role.toLowerCase()) {
            case "admin" -> adminService.saveAdmin(admin);
            case "doctor" -> doctorService.saveDoctor(admin);
            case "nurse" -> nurseService.saveNurse(admin);
            case "patient" -> patientService.savePatient(admin);
        }

        redirectAttributes.addFlashAttribute("signupSuccess", "Registration completed. Please log in.");
        return "redirect:/login?role=" + role;
    }
} 
