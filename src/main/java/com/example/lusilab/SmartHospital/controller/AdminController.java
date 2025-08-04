package com.example.lusilab.SmartHospital.controller;

import com.example.lusilab.SmartHospital.entity.Admin;
import com.example.lusilab.SmartHospital.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admins")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
   private AdminService adminService;


    @GetMapping("/login")
    public String showLoginForm(Model model) {

        if (!model.containsAttribute("admin")) {
            model.addAttribute("admin", new Admin());
        }
        logger.debug("Showing admin login form.");
        return "admins/login";
    }

    @PostMapping("/login")
    public String loginAdmin(@ModelAttribute("admin") Admin admin,
                             RedirectAttributes redirectAttributes, Model model) {


        logger.info("Attempting login for email: {}", admin.getEmail());

        Optional<Admin> loggedInAdmin = adminService.login(admin.getEmail(), admin.getPassword());

        if (loggedInAdmin.isPresent()) {
            logger.info("Admin login successful for email: {}", admin.getEmail());

            return "redirect:/admins/dashboard";
        } else {
            logger.warn("Admin login failed for email: {}", admin.getEmail());
            redirectAttributes.addFlashAttribute("error", "Email or password is not correct.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/admins/login";
        }
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        if (!model.containsAttribute("admin")) {
            model.addAttribute("admin", new Admin());
        }
        logger.debug("Showing admin signup form.");
        return "admins/signup";
    }

    @PostMapping("/signup")
    public String registerAdmin(@ModelAttribute("admin") Admin admin, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Model model) {


        if (admin.getName() == null || admin.getName().trim().isEmpty() ||
                admin.getUsername() == null || admin.getUsername().trim().isEmpty() ||
                admin.getEmail() == null || admin.getEmail().trim().isEmpty() ||
                admin.getPassword() == null || admin.getPassword().isEmpty()) {

            redirectAttributes.addFlashAttribute("error", "Please fill in all fields.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/admins/signup";
        }


        logger.info("Attempting to register admin with email: {}", admin.getEmail());
        if (adminService.existsByEmail(admin.getEmail())) {
            logger.warn("Admin signup failed. Email already exists: {}", admin.getEmail());
            redirectAttributes.addFlashAttribute("error", "This email already exist.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/admins/signup";
        }

        adminService.saveAdmin(admin);
        logger.info("Admin registered successfully with email: {}", admin.getEmail());
        redirectAttributes.addFlashAttribute("signupSuccess", "The registration was completed successfully! You can log in now.");
        return "redirect:/admins/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        logger.debug("Accessing admin dashboard.");
        return "admins/dashboard";
    }
    }