package com.example.lusilab.SmartHospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// Sigurohu qe paketa eshte e sakte
@Controller
public class RoleController {

    @GetMapping("/choose-role")
    public String showChooseRolePage(Model model) {
        model.addAttribute("pageTitle", "SmartHospital - Zgjidhni Rolin");
        return "choose-role";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

    @GetMapping("/dashboard")
        public String showDashboardPage(Model model){
            model.addAttribute("pageTitle","Dashboard");
            return "admin/dashboard";
        }


    @GetMapping("/")
    public String redirectToChooseRole() {
        return "redirect:/login";
    }
}