package com.example.lusilab.SmartHospital.controller;

import com.example.lusilab.SmartHospital.entity.Admin;
import com.example.lusilab.SmartHospital.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    private static final Logger logger = LoggerFactory.getLogger(AdminViewController.class);
    private final AdminService adminService;

    @Autowired
    public AdminViewController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        if (!model.containsAttribute("admin")) {
            model.addAttribute("admin", new Admin());
        }
        logger.debug("Po shfaqet formulari i login-it për adminin.");
        return "admin/login";
    }

    @PostMapping("/login")
    public String loginAdmin(@ModelAttribute("admin") Admin admin, RedirectAttributes redirectAttributes) {
        logger.info("Tentativë për login me email: {}", admin.getEmail());
        Optional<Admin> loggedInAdmin = adminService.login(admin.getEmail(), admin.getPassword());

        if (loggedInAdmin.isPresent()) {
            logger.info("Login i suksesshëm për adminin me email: {}", admin.getEmail());
            return "redirect:/admin/dashboard";
        } else {
            logger.warn("Login i dështuar për email: {}", admin.getEmail());
            redirectAttributes.addFlashAttribute("error", "Email-i ose fjalëkalimi nuk është i saktë.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/admin/login";
        }
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        if (!model.containsAttribute("admin")) {
            model.addAttribute("admin", new Admin());
        }
        logger.debug("Po shfaqet formulari i regjistrimit për adminin.");
        return "admin/signup";
    }

    @PostMapping("/signup")
    public String registerAdmin(@ModelAttribute("admin") Admin admin, RedirectAttributes redirectAttributes) {
        if (admin.getName() == null || admin.getName().trim().isEmpty() ||
                admin.getUserName() == null || admin.getUserName().trim().isEmpty() ||
                admin.getEmail() == null || admin.getEmail().trim().isEmpty() ||
                admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {

            redirectAttributes.addFlashAttribute("error", "Ju lutem plotësoni të gjitha fushat e kërkuara.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/admin/signup";
        }

        logger.info("Tentativë për të regjistruar adminin me email: {}", admin.getEmail());
        if (adminService.existsByEmail(admin.getEmail())) {
            logger.warn("Regjistrimi dështoi. Email-i ekziston: {}", admin.getEmail());
            redirectAttributes.addFlashAttribute("error", "Ky email ekziston tashmë.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/admin/signup";
        }

        adminService.saveAdmin(admin);
        logger.info("Admini u regjistrua me sukses me email: {}", admin.getEmail());
        redirectAttributes.addFlashAttribute("signupSuccess", "Regjistrimi u krye me sukses! Tani mund të hyni.");
        return "redirect:/admin/login";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        logger.debug("Po aksesohet paneli i adminit.");
        return "admin/dashboard";
    }
}